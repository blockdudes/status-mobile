(ns status-im.network.net-info
  (:require ["@react-native-community/netinfo" :default net-info]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [status-im.mobile-sync-settings.core :as mobile-network]
            [status-im.native-module.core :as status]
            [status-im.utils.fx :as fx]
            [status-im.wallet-connect-legacy.core :as wallet-connect-legacy]
            [status-im.wallet.core :as wallet]
            [taoensso.timbre :as log]))

(fx/defn change-network-status
  [{:keys [db] :as cofx} is-connected? isInternetReachable app-went-offline?]
  (fx/merge cofx
            (when (and is-connected?
                       isInternetReachable
                       @app-went-offline?)
              (swap! app-went-offline? not)
              (wallet-connect-legacy/get-connector-session-from-db))
            {:db (assoc db :network-status (if is-connected? :online :offline))}
            (when (and is-connected?
                       (or (not= (count (get-in db [:wallet :accounts]))
                                 (count (get db :multiaccount/accounts)))
                           (wallet/has-empty-balances? db)))
              (wallet/update-balances nil nil))))

(fx/defn change-network-type
  [{:keys [db] :as cofx} old-network-type network-type expensive?]
  (fx/merge cofx
            {:db (assoc db :network/type network-type)
             :network/notify-status-go [network-type expensive?]}
            (mobile-network/on-network-status-change)))

(fx/defn handle-network-info-change
  {:events [::network-info-changed]}
  [{:keys [db] :as cofx} {:keys [isConnected type details isInternetReachable app-went-offline?] :as state}]
  (let [old-network-status  (:network-status db)
        old-network-type    (:network/type db)
        connectivity-status (if isConnected :online :offline)
        status-changed?     (not= connectivity-status old-network-status)
        type-changed?       (= type old-network-type)
        _                   (when (and (not isConnected)
                                       (not @app-went-offline?))
                              (swap! app-went-offline? not))]
    (log/debug "[net-info]"
               "old-network-status"  old-network-status
               "old-network-type"    old-network-type
               "connectivity-status" connectivity-status
               "type"                type
               "details"             details)
    (fx/merge cofx
              (when status-changed?
                (change-network-status isConnected isInternetReachable app-went-offline?))
              (when-not type-changed?
                (change-network-type old-network-type type (:is-connection-expensive details))))))

(defn add-net-info-listener []
  (let [app-went-offline?       (reagent/atom false)]
    (when net-info
      (.addEventListener ^js net-info
                         #(re-frame/dispatch [::network-info-changed
                                              (-> (js->clj % :keywordize-keys true)
                                                  (assoc :app-went-offline? app-went-offline?))])))))

(re-frame/reg-fx
 ::listen-to-network-info
 (fn []
   (add-net-info-listener)))

(re-frame/reg-fx
 :network/notify-status-go
 (fn [[network-type expensive?]]
   (status/connection-change network-type expensive?)))
