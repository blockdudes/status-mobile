(ns quo2.components.community.discover-card
  (:require
   [status-im.react-native.resources :as resources]
   [status-im.ui.components.react :as react]))

(def placeholder-images
  [{:id 1   :image   (resources/get-image :placeholder-image1)}
   {:id 2   :image   (resources/get-image :placeholder-image2)}
   {:id 3   :image   (resources/get-image :placeholder-image3)}
   {:id 4   :image   (resources/get-image :placeholder-image4)}
   {:id 5   :image   (resources/get-image :placeholder-image5)}])


;;    {:id :6   :image   (resources/get-image :placeholder-image6)}
;; {:id :7   :image   (resources/get-image :placeholder-image7)}
;; {:id :8   :image   (resources/get-image :placeholder-image8)}
;; {:id :9   :image   (resources/get-image :placeholder-image9)}


(defn view []
  [:<>
   [react/view {:flex-direction     :row}
    (for [{:keys [id image]} placeholder-images]
      ^{:key id}
      [react/image {:source image
                    :style  {:flex          1
                             :border-radius 20
                             :margin-right  (when (< id 5) 8)
                             :width         (when (and (> id 1) (< id 5)) 80)
                             :height        (when (and (> id 1) (< id 5)) 80)}}])]
   [react/view {:flex-direction     :row
                :margin-top         8}
    (for [{:keys [id image]} placeholder-images]
      ^{:key id}
      [react/image {:source image
                    :style  {:flex          1
                             :border-radius 20
                             :margin-right  (when (< id 5) 8)
                             :width         (when (and (> id 1) (< id 5)) 80)
                             :height        (when (and (> id 1) (< id 5)) 80)}}])]])