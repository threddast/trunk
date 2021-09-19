(ns app.renderer.db)



(def default-db
  {:current-view     "article-list"
   :articles         nil
   :words            []
   :current-article  nil
   :current-word-idx 0
   :current-word     nil
   :loading?         false
   :toast            ""
   :t-win            {:open?    false
                      :loading? false}
   })
