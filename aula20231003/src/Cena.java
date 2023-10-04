import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;

import java.awt.*;

/**
 *
 * @author Kakugawa
 */
public class Cena implements GLEventListener{    
    private float xMin, xMax, yMin, yMax, zMin, zMax;    
    GLU glu;
    private TextRenderer textRenderer;
    public float horizontal, vertical;
    public boolean lightOn;
    
    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena
        glu = new GLU();
        GL2 gl = drawable.getGL().getGL2();
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -100;
        xMax = yMax = zMax = 100;  
        
        //Habilita o buffer de profundidade
        gl.glEnable(GL2.GL_DEPTH_TEST);

        textRenderer = new TextRenderer(new Font("Arial", Font.BOLD, 32));

        horizontal = vertical = 0;
        lightOn = false;
    }

    @Override
    public void display(GLAutoDrawable drawable) {  
        //obtem o contexto Opengl
        GL2 gl = drawable.getGL().getGL2();                
        //objeto para desenho 3D
        GLUT glut = new GLUT();
        
        //define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0, 0, 0, 1);        
        //limpa a janela com a cor especificada
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);       
        gl.glLoadIdentity(); //ler a matriz identidade
        
        /*
            desenho da cena        
        *
        */
        desenhaTexto(gl,200, 570, Color.RED, "FERRARI");

        if(lightOn){
            iluminacaoAmbiente(gl);
            ligaLuz(gl);
        }

        gl.glRotatef(horizontal, 1, 0, 0);
        gl.glRotatef(vertical, 0, 1, 0);

        desenhaCarcaca(gl, glut);
        desenhaCapota(gl, glut);
        desenhaFarol(gl, glut);
        desenhaLanternaTraseira(gl, glut);
        desenhaRodaDianteira(gl, glut);
        desenhaRodaTraseira(gl, glut);

        desligaluz(gl);

        gl.glFlush();      
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {    
        //obtem o contexto grafico Opengl
        GL2 gl = drawable.getGL().getGL2();  
        
        //evita a divisao por zero
        if(height == 0) 
			height = 1;
                
        //ativa a matriz de projecao
        gl.glMatrixMode(GL2.GL_PROJECTION);      
        gl.glLoadIdentity(); //ler a matriz identidade

        //projecao ortogonal sem a correcao do aspecto
        gl.glOrtho(xMin, xMax, yMin, yMax, zMin, zMax);
        
        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); //ler a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }    
       
    @Override
    public void dispose(GLAutoDrawable drawable) {}

    public void desenhaTexto(GL2 gl, int xPosicao, int yPosicao, Color cor, String frase){
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        //Retorna a largura e altura da janela
        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
        textRenderer.setColor(cor);
        textRenderer.draw(frase, xPosicao, yPosicao);
        textRenderer.endRendering();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
    }

    public void desenhaCarcaca(GL2 gl, GLUT glut){
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glPushMatrix();
            gl.glScalef(2, 1, 4);
            glut.glutSolidCube(30);
        gl.glPopMatrix();
    }

    public void desenhaCapota(GL2 gl, GLUT glut){
        gl.glColor3f(1.0f, 1.0f, 0.5f);
        gl.glPushMatrix();
            gl.glTranslatef(0,20,-30);
            gl.glScalef(2, 1, 2);
            glut.glutSolidCube(30);
        gl.glPopMatrix();
    }

    public void desenhaFarol(GL2 gl, GLUT glut){
        gl.glColor3f(1,1,1);
        gl.glPushMatrix();
            gl.glRotatef(90, 0,0, 1);
            gl.glTranslatef(0, 15, 60);
            glut.glutSolidCylinder(5, 5, 30, 30);
        gl.glPopMatrix();

        gl.glPushMatrix();
            gl.glRotatef(90, 0,0, 1);
            gl.glTranslatef(0, -15, 60);
            glut.glutSolidCylinder(5, 5, 30, 30);
        gl.glPopMatrix();
    }

    public void desenhaLanternaTraseira(GL2 gl, GLUT glut){
        gl.glColor3f(1,0.5f,0.5f);
        gl.glPushMatrix();
            gl.glRotatef(90, 0,0, 1);
            gl.glTranslatef(0, 15, -60);
            glut.glutSolidTorus(2, 4,20, 20);
        gl.glPopMatrix();

        gl.glPushMatrix();
            gl.glRotatef(90, 0,0, 1);
            gl.glTranslatef(0, -15, -60);
            glut.glutSolidTorus(2, 4,20, 20);
        gl.glPopMatrix();
    }

    public void desenhaRodaDianteira(GL2 gl, GLUT glut){
        gl.glColor3f(0.3f, 0.3f, 0.3f);
        gl.glPushMatrix();
            gl.glRotatef(90, 0, 1, 0);
            gl.glTranslatef(-60,-15, 30);
            glut.glutSolidTorus(5, 10, 40, 40);
        gl.glPopMatrix();

        gl.glPushMatrix();
            gl.glRotatef(90, 0, 1, 0);
            gl.glTranslatef(-60,-15, -30);
            glut.glutSolidTorus(5, 10, 40, 40);
        gl.glPopMatrix();
    }

    public void desenhaRodaTraseira(GL2 gl, GLUT glut){
        gl.glColor3f(0.3f, 0.3f, 0.3f);
        gl.glPushMatrix();
            gl.glRotatef(90, 0, 1, 0);
            gl.glTranslatef(60,-15, 30);
            glut.glutSolidTorus(5, 10, 40, 40);
        gl.glPopMatrix();

        gl.glPushMatrix();
            gl.glRotatef(90, 0, 1, 0);
            gl.glTranslatef(60,-15, -30);
            glut.glutSolidTorus(5, 10, 40, 40);
        gl.glPopMatrix();
    }
    public void iluminacaoAmbiente(GL2 gl) {
        float luzAmbiente[] = {0.5f, 0.5f, 0.5f, 1f}; //cor
        float posicaoLuz[] = {50.0f, 100.0f, 100.0f, 1.0f}; //pontual

        // define parametros de luz de n�mero 0 (zero)
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicaoLuz, 0);
    }

    public void ligaLuz(GL2 gl) {
        // habilita a defini��o da cor do material a partir da cor corrente
        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        // habilita o uso da ilumina��o na cena
        gl.glEnable(GL2.GL_LIGHTING);
        // habilita a luz de n�mero 0
        gl.glEnable(GL2.GL_LIGHT0);
        //Especifica o Modelo de tonalizacao a ser utilizado
        //GL_FLAT -> modelo de tonalizacao flat
        //GL_SMOOTH -> modelo de tonaliza��o GOURAUD (default)
        gl.glShadeModel(GL2.GL_SMOOTH);
    }
    public void desligaluz(GL2 gl) {
        //desabilita o ponto de luz
        gl.glDisable(GL2.GL_LIGHT0);
        //desliga a iluminacao
        gl.glDisable(GL2.GL_LIGHTING);
    }
}
