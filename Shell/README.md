간단한 쉘 인터프리터를 작성한다. 이 인터프리터는 linux 명령어를 읽고 해석하여 실행시킨다.  

명령어는 shell에서 입력한 명령과 동일하게 실행되어야한다.

이 인터프리터의 기능은 다음과 같다. 



(1) 명령어 실행(Command execution) 

	[prompt] command





(2) 후면 실행(Background execution) 

	[prompt] command &



(3)  종료

	[prompt] exit  또는

	[prompt] logout



(4)  파이프(한개만 처리)

	[prompt] command | command2



(5) Redirection(입출력 모두)

	[prompt] cmd < input > output



추가기능



(6) Background process 관리

	[prompt] myjobs

            현재 background 명령어들과 그들의 process ID를 출력

    

구현방법: 

1. backgroung process를 실행할 때마다 프로그램이름과 process ID 리스트를  background process list에 저장

2. foreground process에 대해서는 forground process가 실행 중임을 making하고,  wait 대신에 pause()를 사용. pause에서 깨어날 때 foregroung가

여전히 실행중이면 다시 pause()를 반복

3. SIG_CHLD (death of chile) 시그널을 catch하여 signal handler에서 waitpid로 어떤 프로세스가 종료되었는지 알아내고 

background process이면 background process list에서 삭제, foregound process이면 forgroung process가 없다고 marking

