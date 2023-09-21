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
        System.out.println("Key pressed: " + e.getKeyCode());
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
        
        if(e.getKeyChar() == 't') {
            if(cena.transformer) {
                cena.transformer = !cena.transformer;
                cena.angulo = 0;
                cena.scalaY = 1;
                cena.trasnlateX = 0;
                cena.translateY = 0;
            }
            else{
                cena.transformer = !cena.transformer;
                cena.angulo = 20;
                cena.scalaY = 0.5f;
                cena.trasnlateX = 0.3f;
                cena.translateY = 0.1f;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
