(ns quo2.components.tags.status-tags
  (:require [quo2.foundations.colors :as colors]
            [quo.theme :as quo.theme]
            [quo2.components.icon :as icon]
            [quo2.components.markdown.text :as text]
            [quo.react-native :as rn]))

(def default-container-style
  {:border-radius 20
   :border-width 1})

(def small-container-style
  (merge default-container-style
         {:padding-horizontal 8
          :padding-vertical 3}))

(def large-container-style
  (merge default-container-style
         {:padding-horizontal 11
          :padding-vertical 4}))

(defn base-tag [_]
  (fn [{:keys [size
               border-color
               background-color
               icon
               theme
               text-color
               label]}]
    (let [paragraph-size (if (= size :small) :paragraph-2 :paragraph-1)]
      [rn/view
       (assoc (if (= size :small)
                small-container-style
                large-container-style)
              :border-width 1
              :border-color border-color
              :background-color background-color)
       [rn/view {:flex-direction :row
                 :flex 1}
        [rn/view {:style {:justify-content :center
                          :align-items :center}}
         [icon/icon-for-theme
          icon
          theme
          {:no-color true
           :size 12}]]
        [text/text {:size paragraph-size
                    :weight :medium
                    :style {:padding-left 5
                            :color text-color}} label]]])))

(defn- positive
  [status size theme]
  [base-tag {:size             size
             :background-color colors/success-50-opa-10
             :icon             :verified
             :border-color     colors/success-50-opa-20
             :label            (:label status)
             :text-color       (if (= theme :light) colors/success-50
                                   colors/success-60)}])

(defn- negative
  [status size theme]
  [base-tag {:size             size
             :icon             :untrustworthy
             :background-color colors/danger-50-opa-10
             :border-color     colors/danger-50-opa-20
             :label            (:label status)
             :text-color       (if (= theme :light)
                                 colors/danger-50
                                 colors/danger-60)}])

(defn- pending
  [status size theme]
  [base-tag {:size             size
             :icon             :pending
             :label            (:label status)
             :background-color (if (= theme :light)
                                 colors/neutral-10
                                 colors/neutral-80)
             :border-color     (if (= theme :light)
                                 colors/neutral-20
                                 colors/neutral-70)
             :text-color       colors/neutral-50}])

(defn status-tag [{:keys [status size override-theme]}]
  (when status
    (when-let [status-component (case (:type status)
                                  :positive positive
                                  :negative negative
                                  :pending  pending
                                  nil)]
      [status-component
       status
       size
       (or override-theme (quo.theme/get-theme))])))
