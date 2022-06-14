#include <GL/glut.h>
#include <math.h>
float height = 0, gTime = 0, seta,x=0,z=0,size=1;
bool check = FALSE;
void DrawObject() {
	glColor3f(0.3, 0.3, 0.7);

	seta = 0.7*sin(gTime*0.05); // -0.7 ~ +0.7 ��

	glPushMatrix();
		glScalef(4, 0.2, 0.2);
		glutWireCube(0.6); //�� ���� �ִ� ��
		glPushMatrix();
			glRotatef(seta, 0, 0, 1); //seta���� �̿��ؼ� �����ϰ� �Դٰ��� �����̰� �Ѵ�.
			glTranslatef(0.17, -5, 0);
			glScalef(0.01, 10, 0.001);
			glutWireCube(1.0); //������ �������� ��1

			glTranslatef(0, -0.5, 0);
			glScalef(2.5, 0.05, 500);
			glutWireSphere(1, 20, 20); //�ٿ� �޷��ִ� ����1
		glPopMatrix();

		glTranslatef(-0.065, 0, 0);
		glPushMatrix();
			glRotatef(seta * 20, 1, 0, 0); //seta���� �̿��ؼ� �����ϰ� �Դٰ��� �����̰� �Ѵ�.
			glTranslatef(0.17, -5, 0);
			glScalef(0.01, 10, 0.001);
			glutWireCube(1.0); //������ �������� ��2

			glTranslatef(0, -0.5, 0);
			glScalef(2.5, 0.05, 500);
			glutWireSphere(1, 20, 20); //�ٿ� �޷��ִ� ����2
		glPopMatrix();

		glTranslatef(-0.065, 0, 0);
			glPushMatrix();
			glRotatef(-seta * 20, 1, 0, 0); //seta���� �̿��ؼ� �����ϰ� �Դٰ��� �����̰� �Ѵ�.
			glTranslatef(0.17, -5, 0);
			glScalef(0.01, 10, 0.001);
			glutWireCube(1.0); //������ �������� ��3

			glTranslatef(0, -0.5, 0);
			glScalef(2.5, 0.05, 500);
			glutWireSphere(1, 20, 20); //�ٿ� �޷��ִ� ����3
		glPopMatrix();

		glTranslatef(-0.065, 0, 0);
		glPushMatrix();
			glRotatef(seta * 20, 1, 0, 0); //seta���� �̿��ؼ� �����ϰ� �Դٰ��� �����̰� �Ѵ�.
			glTranslatef(0.17, -5, 0);
			glScalef(0.01, 10, 0.001);
			glutWireCube(1.0); //������ �������� ��4

			glTranslatef(0, -0.5, 0);
			glScalef(2.5, 0.05, 500);
			glutWireSphere(1, 20, 20); //�ٿ� �޷��ִ� ����4
		glPopMatrix();

		glTranslatef(-0.065, 0, 0);
		glPushMatrix();
			glRotatef(-seta * 20, 1, 0, 0); //seta���� �̿��ؼ� �����ϰ� �Դٰ��� �����̰� �Ѵ�.
			glTranslatef(0.17, -5, 0);
			glScalef(0.01, 10, 0.001);
			glutWireCube(1.0); //������ �������� ��5

			glTranslatef(0, -0.5, 0);
			glScalef(2.5, 0.05, 500);
			glutWireSphere(1, 20, 20); //�ٿ� �޷��ִ� ����5
		glPopMatrix();

		glTranslatef(-0.065, 0, 0);
		glPushMatrix();
			glRotatef(-seta, 0, 0, 1); //seta���� �̿��ؼ� �����ϰ� �Դٰ��� �����̰� �Ѵ�.
			glTranslatef(0.17, -5, 0);
			glScalef(0.01, 10, 0.001);
			glutWireCube(1.0); //������ �������� ��6

			glTranslatef(0, -0.5, 0);
			glScalef(2.5, 0.04, 500);
			glutWireSphere(1, 20, 20); //�ٿ� �޷��ִ� ����6
		glPopMatrix();
	glPopMatrix();

	//�عٴ�
	glPushMatrix();
		glTranslatef(0, -2.5, 0);
		glScalef(4, 0.2, 2);
		glutWireCube(0.6);
	glPopMatrix();

	//���1
	glPushMatrix();
		glRotatef(12, 1, 0, 0);
		glTranslatef(-1.15, -1.3, 0);
		glScalef(0.2, 4.3, 0.2);
		glutWireCube(0.6);
	glPopMatrix();

	//���2
	glPushMatrix();
		glRotatef(-12, 1, 0, 0);
		glTranslatef(-1.15, -1.3, 0);
		glScalef(0.2, 4.3, 0.2);
		glutWireCube(0.6);
	glPopMatrix();

	//���3
	glPushMatrix();
		glRotatef(12, 1, 0, 0);
		glTranslatef(1.15, -1.3, 0);
		glScalef(0.2, 4.3, 0.2);
		glutWireCube(0.6);
	glPopMatrix();

	//���4
	glPushMatrix();
		glRotatef(-12, 1, 0, 0);
		glTranslatef(1.15, -1.3, 0);
		glScalef(0.2, 4.3, 0.2);
		glutWireCube(0.6);
	glPopMatrix();


}

void MyKeyboard(unsigned char KeyPressed, int X, int Y) { 
	switch (KeyPressed) {
	case 'c': //����(ũ�� ����) ������ �ٲ��ش�.
		check = !check;
		glutPostRedisplay(); //�׸��� �ٽ� �׸��� �̺�Ʈ�� �߻� -> Mydisplay()
		break;
	case 'd': // ��ü�� �Ʒ��� ��������.
		height -= 0.1; 
		glutPostRedisplay(); 
		break;
	case 'e': // ��ü�� ���� �ö󰣴�.
		height += 0.1; 
		glutPostRedisplay(); 
		break;
	case 'a': // ��ü�� ũ�Ⱑ Ŀ����.
		size += 0.05;
		glutPostRedisplay();
		break;
	case 's': // ��ü�� ũ�Ⱑ �۾�����.
		size -= 0.05;
		glutPostRedisplay(); 
		break;
	case 27:                //'esc' Ű�� �ƽ�Ű �ڵ� ��
		exit(0); break;
	}
}
void MyDisplay() {
	glClear(GL_COLOR_BUFFER_BIT);

	x = 2 * cos(gTime*0.03);
	z = 2 * sin(gTime*0.03);
	if (check == TRUE) { // ���� ������ �ٲٱ� ���� x,y�� ���� ���� ��ȯ�ߴ�.
		float temp;
		temp = x;
		x = z;
		z = temp;
	}
	glPushMatrix();
		glTranslatef(x-1, height, z-1); //ũ�� ����, Ű���� �������� ��, �Ʒ��� �����δ�.
		glRotatef(gTime*0.5, 0, 1, 0); //���ڸ����� ����
		glScalef(0.7*size,0.7*size,0.7*size); //Ű���� �������� ũ�⸦ �����ȴ�.
		DrawObject();
	glPopMatrix();
	glFlush();
}

void MyReshape(int w, int h) {
	glViewport(0, 0, (GLsizei)w, (GLsizei)h);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	// ��Ⱦ��(���ο� ���� ������ ����. 4:3 �̶�� 1.333 �Է�)
	gluPerspective(30, (GLdouble)w / (GLdouble)h, 1.0, 50.0);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	gluLookAt(5.0, 5.0, 5.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
	glutPostRedisplay();
}
void MyTimer(int x) {
	gTime += 1.0;
	glutTimerFunc(20, MyTimer, 0);
	glutPostRedisplay();
}
int main(int argc, char** argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGBA);
	glutInitWindowSize(500, 500);
	glutInitWindowPosition(0, 0);
	glutCreateWindow("OpenGL Sample Drawing");
	glClearColor(1.0, 1.0, 1.0, 1.0);
	glutDisplayFunc(MyDisplay);
	glutReshapeFunc(MyReshape);
	glutKeyboardFunc(MyKeyboard);
	glutTimerFunc(20, MyTimer, 0);
	glutMainLoop();
	return 0;
}
