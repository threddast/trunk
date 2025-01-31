(ns app.main.windows
  (:require
   ["electron" :refer [BrowserWindow BrowserView app dialog]]))

(def main-window (atom nil))
(def t-win (atom nil))

(defn init-browser
  []
  (reset! main-window
          (BrowserWindow.
           (clj->js {:width  1300
                     :height 800
                     :minWidth 600
                     :webPreferences
                     {:nodeIntegration  true
                      :contextIsolation false}}))) ;; come back and figure out preload.js someday.

  ;; (init-browser-view)
  (.loadURL ^js/electron.BrowserWindow @main-window (str "file://" js/__dirname "/public/index.html"))
  (.on ^js/electron.BrowserWindow @main-window "closed" #(reset! main-window nil)))

(defn go-to-url
  [{:keys [word-or-phrase target-lang native-lang]}]
  (.loadURL ^js (.-webContents ^BrowserView @t-win)
           ; (if (and word-or-phrase target-lang native-lang)
            (str "https://ordbokene.no/bm/search?q=" word-or-phrase "&scope=ei&perPage=20")))
              ;; str "https://translate.google.com"
              ;; "?sl=" target-lang "&tl=" native-lang
              ;; "&text=" word-or-phrase "&op=translate"
            ;  "https://translate.google.com")))

(defn t-win-init
  "Used for mini google translate window."
  [{:keys [width height containerHeight button-height containerWidth] :as data}]
  (let [pos {:x      (- width (int containerWidth))
             :y      (- height button-height (int containerHeight))
             :width  (int containerWidth)
             :height (int containerHeight)}]
    (reset! t-win (BrowserView.))
    (.setBrowserView ^js/electron.BrowserWindow @main-window @t-win)
    (.setBounds ^js/electron.BrowserView @t-win (clj->js pos))
    (go-to-url (select-keys data [:word-or-phrase :target-lang :native-lang]))))

(defn t-win-update-word
  [data]
  (go-to-url (select-keys data [:word-or-phrase :target-lang :native-lang])))

(defn t-win-close
  []
  (.removeBrowserView ^js/electron.BrowserWindow @main-window @t-win)
  (reset! t-win nil))

(defn bkup-db-window?
  [backup-name]
  (let [path (.getPath app "desktop")
        opts (clj->js {:title       "Select save location for Trunk backup"
                       :defaultPath (str path "/" backup-name)})]
    (.showSaveDialog dialog opts)))

(defn restore-db-window?
  []
  (let [opts (clj->js {:title      "Select a Trunk database backup."
                       :filters    [{:name "Trunk Database" :extensions ["db"]}]
                       :properties ["openFile"]})]
    (.showOpenDialog dialog opts)))
