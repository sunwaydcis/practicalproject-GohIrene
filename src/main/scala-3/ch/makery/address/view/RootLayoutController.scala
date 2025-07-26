package ch.makery.address.view

import ch.makery.address.MainApp
import javafx.event.ActionEvent
import javafx.fxml.FXML

@FXML
class RootLayoutController():
  //when someone click close, it should call function
    // create function
    @FXML
    def handleClose(action: ActionEvent): Unit = 
      // only windows can close (main apps)
      MainApp.stage.close() // import main apps then can direct use the object