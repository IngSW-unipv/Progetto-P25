package proggeto;


import ui.UI;

import javax.swing.*;
import java.util.ArrayList;

public class App {
    //launching the app
    public void run(){
        SwingUtilities.invokeLater(UI::new);
    }


}
