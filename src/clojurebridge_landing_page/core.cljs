(ns ^:figwheel-hooks clojurebridge-landing-page.core
  (:require
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]))

;; Debugging
;;;;;;;;;;;;;;;;;;;;;;;;

(println (js/Date.) "Reloading: src/clojurebridge_landing_page/core.cljs")

;; Helper functions
;;;;;;;;;;;;;;;;;;;;;;;;

(defn multiply [a b] (* a b))


;; Application state
;;;;;;;;;;;;;;;;;;;;;;;;

;; define your app data so that it doesn't get over-written on reload
(defonce
  app-state
    (atom
      {:text "Hello world!"
       :sponsors
         {:current
           {:name    "Functional Works"
            :logo    "images/functional-works-logo.png"
            :website "https://functional.works-hub.com/"
            :message "Breaking down the barriers to hiring the right software engineers,
            providing a platform to managing the whole process (written in ClojureScript)."}
          :past    {:9 {,,,}}}}))

;; Content functions
;;;;;;;;;;;;;;;;;;;;;;;;

(defn sponsor-current
  "Sponsors for our current event, to help that sponsor get some exposure

  Argument: hash-map of strings - :name, :website, :logo, :message"
  [sponsor-details]

  [:div {:class "container"}
   [:div {:class "box"}
    [:div {:class "column is-half is-8 is-offset-2"}
     [:a {:href (get sponsor-details :website)}
       [:h3 {:class "title is-5 has-text-centered"}
         (str "Our Sponsors:" " " (get sponsor-details :name))]]
     [:div {:class "columns"}
      [:div {:class "column"}
       [:figure {:class "image"}
        [:a {:href (get sponsor-details :website)}
         [:img {:src "images/functional-works-logo.png"}]]]]
      [:div {:class "column"}
       [:div {:class "content"}
        [:p (get sponsor-details :message)]]]]]]])

(defn welcome-message []
  [:section {:class "section"}

   [:h1
    {:class "title"}
    "Welcome to ClojureBridge"]

   ;; Content will just use HTML tags directly, useful when you have no specific styles
   ;; https://bulma.io/documentation/elements/content/

   [:div {:class "content"}
    [:p
     "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sed enim ante. Nullam consectetur, sapien in rutrum facilisis, augue velit finibus est, at lobortis odio eros sollicitudin risus. Nullam mollis, metus a varius volutpat, metus elit mollis est, finibus pretium dui enim non velit. Praesent sit amet volutpat nulla. Sed volutpat venenatis nisi id sagittis. Nunc nec efficitur mi. Duis consequat sapien ultricies quam bibendum, elementum faucibus sapien bibendum. Morbi diam elit, gravida iaculis metus vitae, ullamcorper mattis mi. Maecenas luctus lorem metus. Maecenas eleifend nisl sit amet felis accumsan, sit amet tincidunt turpis consequat. Cras non molestie ante, a pellentesque dui."]
    [:p
     "Vivamus ullamcorper at orci ac tincidunt. Vivamus tincidunt sed erat nec consequat. Donec venenatis lorem justo, eget imperdiet arcu ultrices vitae. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Donec congue tempor posuere. Sed nec nisl mauris. Maecenas elementum quam justo, vitae auctor felis dapibus a. Phasellus leo justo, mattis a auctor tempus, facilisis vel tellus. Etiam at scelerisque justo, ac facilisis purus. Duis in leo pretium purus bibendum ultricies ac vitae lectus. Proin nec mi nec urna sollicitudin iaculis. In a orci felis. Sed luctus posuere luctus. Cras id euismod orci, id mollis nibh. Vestibulum et tellus quis lorem placerat scelerisque non et nisl. Ut dictum lacus nulla, sit amet ultricies eros pharetra vitae. "]]])

(defn landing-page []
  [:div
    [welcome-message]
    [sponsor-current (get-in @app-state [:sponsors :current])]])


;; system
;;;;;;;;;;;;;;;;;;;;;;;;

(defn get-app-element []
  (gdom/getElement "app"))

(defn mount [el]
  (reagent/render-component [landing-page] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element))
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;

#_(in-ns 'clojurebridge-landing-page.core)
#_(deref app-state)

#_(reset! app-state
      {:text "Hello world!"
       :sponsors
         {:current
           {:name    "Functional Works"
            :logo    "images/functional-works-logo.png"
            :website "https://functional.works-hub.com/"
            :message "Breaking down the barriers to hiring the right software engineers,
            providing a platform to managing the whole process (written in ClojureScript)."}
          :past    {:9 {,,,}}}})
