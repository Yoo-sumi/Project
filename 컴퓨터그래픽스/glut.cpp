#include <GL/glut.h>
#include <math.h>
float height = 0, gTime = 0, seta,x=0,z=0,size=1;
bool check = FALSE;
void DrawObject() {
	glColor3f(0.3, 0.3, 0.7);

	seta = 0.7*sin(gTime*0.05); // -0.7 ~ +0.7 값

	glPushMatrix();
		glScalef(4, 0.2, 0.2);
		glutWireCube(0.6); //맨 위에 있는 봉
		glPushMatrix();
			glRotatef(seta, 0, 0, 1); //seta값을 이용해서 일정하게 왔다갔다 움직이게 한다.
			glTranslatef(0.17, -5, 0);
			glScalef(0.01, 10, 0.001);
			glutWireCube(1.0); //봉에서 내려오는 줄1

			glTranslatef(0, -0.5, 0);
			glScalef(2.5, 0.05, 500);
			glutWireSphere(1, 20, 20); //줄에 달려있는 구슬1
		glPopMatrix();

		glTranslatef(-0.065, 0, 0);
		glPushMatrix();
			glRotatef(seta * 20, 1, 0, 0); //seta값을 이용해서 일정하게 왔다갔다 움직이게 한다.
			glTranslatef(0.17, -5, 0);
			glScalef(0.01, 10, 0.001);
			glutWireCube(1.0); //봉에서 내려오는 줄2

			glTranslatef(0, -0.5, 0);
			glScalef(2.5, 0.05, 500);
			glutWireSphere(1, 20, 20); //줄에 달려있는 구슬2
		glPopMatrix();

		glTranslatef(-0.065, 0, 0);
			glPushMatrix();
			glRotatef(-seta * 20, 1, 0, 0); //seta값을 이용해서 일정하게 왔다갔다 움직이게 한다.
			glTranslatef(0.17, -5, 0);
			glScalef(0.01, 10, 0.001);
			glutWireCube(1.0); //봉에서 내려오는 줄3

			glTranslatef(0, -0.5, 0);
			glScalef(2.5, 0.05, 500);
			glutWireSphere(1, 20, 20); //줄에 달려있는 구슬3
		glPopMatrix();

		glTranslatef(-0.065, 0, 0);
		glPushMatrix();
			glRotatef(seta * 20, 1, 0, 0); //seta값을 이용해서 일정하게 왔다갔다 움직이게 한다.
			glTranslatef(0.17, -5, 0);
			glScalef(0.01, 10, 0.001);
			glutWireCube(1.0); //봉에서 내려오는 줄4

			glTranslatef(0, -0.5, 0);
			glScalef(2.5, 0.05, 500);
			glutWireSphere(1, 20, 20); //줄에 달려있는 구슬4
		glPopMatrix();

		glTranslatef(-0.065, 0, 0);
		glPushMatrix();
			glRotatef(-seta * 20, 1, 0, 0); //seta값을 이용해서 일정하게 왔다갔다 움직이게 한다.
			glTranslatef(0.17, -5, 0);
			glScalef(0.01, 10, 0.001);
			glutWireCube(1.0); //봉에서 내려오는 줄5

			glTranslatef(0, -0.5, 0);
			glScalef(2.5, 0.05, 500);
			glutWireSphere(1, 20, 20); //줄에 달려있는 구슬5
		glPopMatrix();

		glTranslatef(-0.065, 0, 0);
		glPushMatrix();
			glRotatef(-seta, 0, 0, 1); //seta값을 이용해서 일정하게 왔다갔다 움직이게 한다.
			glTranslatef(0.17, -5, 0);
			glScalef(0.01, 10, 0.001);
			glutWireCube(1.0); //봉에서 내려오는 줄6

			glTranslatef(0, -0.5, 0);
			glScalef(2.5, 0.04, 500);
			glutWireSphere(1, 20, 20); //줄에 달려있는 구슬6
		glPopMatrix();
	glPopMatrix();

	//밑바닥
	glPushMatrix();
		glTranslatef(0, -2.5, 0);
		glScalef(4, 0.2, 2);
		glutWireCube(0.6);
	glPopMatrix();

	//기둥1
	glPushMatrix();
		glRotatef(12, 1, 0, 0);
		glTranslatef(-1.15, -1.3, 0);
		glScalef(0.2, 4.3, 0.2);
		glutWireCube(0.6);
	glPopMatrix();

	//기둥2
	glPushMatrix();
		glRotatef(-12, 1, 0, 0);
		glTranslatef(-1.15, -1.3, 0);
		glScalef(0.2, 4.3, 0.2);
		glutWireCube(0.6);
	glPopMatrix();

	//기둥3
	glPushMatrix();
		glRotatef(12, 1, 0, 0);
		glTranslatef(1.15, -1.3, 0);
		glScalef(0.2, 4.3, 0.2);
		glutWireCube(0.6);
	glPopMatrix();

	//기둥4
	glPushMatrix();
		glRotatef(-12, 1, 0, 0);
		glTranslatef(1.15, -1.3, 0);
		glScalef(0.2, 4.3, 0.2);
		glutWireCube(0.6);
	glPopMatrix();


}

void MyKeyboard(unsigned char KeyPressed, int X, int Y) { 
	switch (KeyPressed) {
	case 'c': //도는(크게 돌기) 방향을 바꿔준다.
		check = !check;
		glutPostRedisplay(); //그림을 다시 그리는 이벤트를 발생 -> Mydisplay()
		break;
	case 'd': // 물체가 아래로 내려간다.
		height -= 0.1; 
		glutPostRedisplay(); 
		break;
	case 'e': // 물체가 위로 올라간다.
		height += 0.1; 
		glutPostRedisplay(); 
		break;
	case 'a': // 물체의 크기가 커진다.
		size += 0.05;
		glutPostRedisplay();
		break;
	case 's': // 물체의 크기가 작아진다.
		size -= 0.05;
		glutPostRedisplay(); 
		break;
	case 27:                //'esc' 키의 아스키 코드 값
		exit(0); break;
	}
}
void MyDisplay() {
	glClear(GL_COLOR_BUFFER_BIT);

	x = 2 * cos(gTime*0.03);
	z = 2 * sin(gTime*0.03);
	if (check == TRUE) { // 도는 방향을 바꾸기 위해 x,y의 값을 서로 교환했다.
		float temp;
		temp = x;
		x = z;
		z = temp;
	}
	glPushMatrix();
		glTranslatef(x-1, height, z-1); //크게 돌기, 키보드 조작으로 위, 아래로 움직인다.
		glRotatef(gTime*0.5, 0, 1, 0); //제자리에서 돌기
		glScalef(0.7*size,0.7*size,0.7*size); //키보드 조작으로 크기를 조절된다.
		DrawObject();
	glPopMatrix();
	glFlush();
}

void MyReshape(int w, int h) {
	glViewport(0, 0, (GLsizei)w, (GLsizei)h);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	// 종횡비(세로에 대한 가로의 비율. 4:3 이라면 1.333 입력)
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
