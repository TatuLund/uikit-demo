# UIkit demo with Vaadin

Vaadin in addition its own components has Java API for standard html elements. This makes possible to use standard html markup based toolkits such as Bootstrap or UIkit. This demo app is a proof of concept how to use UIkit with Vaadin.

## Notes

* This is not a best practice example, just proof of concept
* You can't use Vaadin normal components fully, in order to keep the UI compatible with UIkit
* You will miss useful funcionality Vaadin provides normally with its own components

## Runnoing the app

Run using `mvn jetty:run` and open [http://localhost:8080](http://localhost:8080) in the browser.

If you want to run your app locally in the production mode, run `mvn jetty:run -Pproduction`.

