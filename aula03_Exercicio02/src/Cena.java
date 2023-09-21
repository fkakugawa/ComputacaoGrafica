import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
/**
 *
 * @author Kakugawa
 */
public class Cena implements GLEventListener{    
    private float xMin, xMax, yMin, yMax, zMin, zMax;    
    GLU glu;
    float angulo;
    float scalaY;
    float trasnlateX;
    float translateY;
    boolean transformer;
    
    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena
        glu = new GLU();
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -1;
        xMax = yMax = zMax = 1;

        transformer = false;
        angulo = 0;
        scalaY = 1;
        trasnlateX = 0;
        translateY = 0;
    }

    @Override
    public void display(GLAutoDrawable drawable) {  
        //obtem o contexto Opengl
        GL2 gl = drawable.getGL().getGL2();                
        //define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0, 1, 0, 1);
        //limpa a janela com a cor especificada
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);       
        gl.glLoadIdentity(); //lê a matriz identidade
        
        /*
            desenho da cena        
        *
        */
        
        ceu(gl);
        estrelas(gl);
        parede(gl);

        gl.glPushMatrix();
            gl.glTranslatef(trasnlateX, translateY, 0);
            telhado(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
            gl.glScalef(1, scalaY, 1);
            porta(gl);
        gl.glPopMatrix();

        entrada(gl);

        gl.glPushMatrix();
            gl.glRotatef(angulo,0,0,1);
            janela(gl);
        gl.glPopMatrix();

        gl.glFlush();      
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {    
        //obtem o contexto grafico Opengl
        GL2 gl = drawable.getGL().getGL2();  
        
        //evita a divisão por zero
        if(height == 0) height = 1;
        //calcula a proporção da janela (aspect ratio) da nova janela
        float aspect = (float) width / height;
        
        //seta o viewport para abranger a janela inteira
        gl.glViewport(0, 0, width, height);
                
        //ativa a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);      
        gl.glLoadIdentity(); //lê a matriz identidade
        
        //Projeção ortogonal
        //true:   aspect >= 1 configura a altura de -1 para 1 : com largura maior
        //false:  aspect < 1 configura a largura de -1 para 1 : com altura maior
        if(width >= height)            
            gl.glOrtho(xMin * aspect, xMax * aspect, yMin, yMax, zMin, zMax);
        else        
            gl.glOrtho(xMin, xMax, yMin / aspect, yMax / aspect, zMin, zMax);
                
        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); //lê a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }    
       
    @Override
    public void dispose(GLAutoDrawable drawable) {}

    public void ceu(GL2 gl){
        gl.glColor3f(0.1f,0.1f,0.3f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(1, 0);
            gl.glVertex2f(-1, 0);
            gl.glVertex2f(-1, 1);
            gl.glVertex2f(1, 1);
        gl.glEnd();
    }

    public void estrelas(GL2 gl){
        gl.glColor3f(1,1,1);
        gl.glBegin(GL2.GL_POINTS);
            gl.glVertex2f(-0.65f, 0.3f);
            gl.glVertex2f(-0.8f, 0.5f);
            gl.glVertex2f(-0.7f, 0.7f);
            gl.glVertex2f(-0.9f, 0.7f);
            gl.glVertex2f(-0.8f, 0.9f);
            gl.glVertex2f(-0.4f, 0.8f);
            gl.glVertex2f(-0.5f, 0.9f);
            gl.glVertex2f(0.9f, 0.8f);
            gl.glVertex2f(0.1f, 0.9f);
            gl.glVertex2f(0.2f, 0.7f);
            gl.glVertex2f(0.9f, 0.7f);
            gl.glVertex2f(0.8f, 0.9f);
            gl.glVertex2f(0.5f, 0.9f);
            gl.glVertex2f(0.65f, 0.5f);
            gl.glVertex2f(0.7f, 0.3f);
        gl.glEnd();

        gl.glPointSize(2);
        gl.glBegin(GL2.GL_POINTS);
            gl.glVertex2f(0.6f, 0.8f);
        gl.glEnd();
        gl.glPointSize(1);
    }
    public void parede(GL2 gl){
        float coord = 0.5f;
        gl.glColor3f(1,0,0);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(0.5f, 0.4f);
            gl.glVertex2f(0.5f, -0.6f);
            gl.glVertex2f(-0.5f, -0.6f);
            gl.glVertex2f(-0.5f, 0.4f);
        gl.glEnd();
    }
    public void telhado(GL2 gl){
        float coord = 0.5f;
        gl.glColor3f(0,0,1);
        gl.glBegin(GL2.GL_TRIANGLES);
            gl.glVertex2f(0, 0.9f);
            gl.glVertex2f(0.5f, 0.4f);
            gl.glVertex2f(-0.5f, 0.4f);
        gl.glEnd();
    }
    public void porta(GL2 gl){
        gl.glColor3f(0,1,0);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-0.1f, 0.2f);
            gl.glVertex2f(-0.1f, -0.6f);
            gl.glVertex2f(-0.4f, -0.6f);
            gl.glVertex2f(-0.4f, 0.2f);
        gl.glEnd();

        gl.glPointSize(2);
        gl.glColor3f(0,0,0);
        gl.glBegin(GL2.GL_POINTS);
            gl.glVertex2f(-0.35f, -0.15f);
        gl.glEnd();
        gl.glPointSize(1);
    }

    public void entrada(GL2 gl){
        float coord = 0.5f;
        gl.glColor3f(1,1,0);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-0.1f, -0.6f);
            gl.glVertex2f(0.2f, -1f);
            gl.glVertex2f(-0.6f, -1f);
            gl.glVertex2f(-0.4f, -0.6f);
        gl.glEnd();
    }
    public void janela(GL2 gl){
        gl.glColor3f(0,0,0);
        gl.glLineWidth(2);
        gl.glBegin(GL2.GL_LINE_LOOP);
            gl.glVertex2f(0.0f, 0.2f);
            gl.glVertex2f(0.4f, 0.2f);
            gl.glVertex2f(0.4f, -0.0f);
            gl.glVertex2f(0.0f, -0.0f);
        gl.glEnd();

        gl.glBegin(GL2.GL_LINES);
        gl.glVertex2f(0.0f, 0.1f);
        gl.glVertex2f(0.4f, 0.1f);
        gl.glEnd();

        gl.glBegin(GL2.GL_LINES);
        gl.glVertex2f(0.2f, 0.2f);
        gl.glVertex2f(0.2f, 0.0f);
        gl.glEnd();
        gl.glLineWidth(1);
    }

}
