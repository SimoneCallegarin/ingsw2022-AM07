package it.polimi.ingsw.View.GUI.Buttons;

import javax.swing.*;

import static it.polimi.ingsw.View.GUI.ImagesLoader.exchangeChoiceImageLoader;

/**
 * Button used to take the exchange students choice made by the user.
 */
public class ExchangeChoiceButton extends JButton  {

    /**
     * Constructor of ExchangeChoiceButton.
     * @param exchange integer used to decide which image to load.
     */
    public ExchangeChoiceButton(int exchange) {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        setIcon(new ImageIcon(exchangeChoiceImageLoader(exchange)));
    }

}
