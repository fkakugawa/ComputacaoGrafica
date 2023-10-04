package exercicio;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL2;
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
        
        switch (e.getKeyChar()) {
            case 'r':
                cena.angulo += 45;
                break;
            case 't':
                cena.tonalizacao = cena.tonalizacao == GL2.GL_SMOOTH ? GL2.GL_FLAT : GL2.GL_SMOOTH;
                break;
            case 'l':
                if(cena.liga)
                    cena.liga = false;
                else
                    cena.liga = true;
                System.out.println(cena.liga);
                break;
            case 'w':
                cena.modo = cena.modo == GL2.GL_FILL ? GL2.GL_LINE : GL2.GL_FILL;
                break;           
            case '0':
                cena.ponto = 0;
                System.out.println("ponto: " + cena.ponto);
                break;
            case '1':
                cena.ponto = 1;
                System.out.println("ponto: " + cena.ponto);
                break;       
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
