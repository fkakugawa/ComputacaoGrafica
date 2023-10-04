
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
/**
 *
 * @author Kakugawa
 */
public class KeyBoard implements KeyListener{
    private Cena cena;
    
    public KeyBoard(Cena cena){
        this.cena = cena;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {        

        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                cena.horizontal++;
                break;
            case KeyEvent.VK_DOWN:
                cena.horizontal--;
                break;
            case KeyEvent.VK_LEFT:
                cena.vertical++;
                break;
            case KeyEvent.VK_RIGHT:
                cena.vertical--;
                break;
            case KeyEvent.VK_SPACE:
                cena.lightOn = !cena.lightOn;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
