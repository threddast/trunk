(ns app.renderer.subs
  (:require
   [re-frame.core :as rf]
   [clojure.string :as str]))

(def <| (comp deref rf/subscribe))

;; Top level subs

(rf/reg-sub ::current-view        (fn [db] (db :current-view)))
(rf/reg-sub ::articles            (fn [db] (db :articles)))
(rf/reg-sub ::words               (fn [db] (db :words)))
(rf/reg-sub ::current-article     (fn [db] (db :current-article)))
(rf/reg-sub ::current-word        (fn [db] (db :current-word)))
(rf/reg-sub ::current-word-idx    (fn [db] (db :current-word-idx)))
(rf/reg-sub ::current-phrase-idxs (fn [db] (db :current-phrase-idxs)))
(rf/reg-sub ::current-phrase      (fn [db] (db :current-phrase)))
(rf/reg-sub ::current-phrase-text (fn [db]
                                       (str/join " "
                                                 (->> (db :current-phrase-words)
                                                      (map :name)))))
(rf/reg-sub ::loading?            (fn [db] (db :loading?)))
(rf/reg-sub ::toast               (fn [db] (db :toast)))
(rf/reg-sub ::settings            (fn [db] (db :settings)))
(rf/reg-sub ::shift-held?         (fn [db] (db :shift-held?)))

;; translation window
(rf/reg-sub ::t-win-loading?   (fn [db] (-> db :t-win :loading?)))
(rf/reg-sub ::t-win-open?      (fn [db] (-> db :t-win :open?)))
