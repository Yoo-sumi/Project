<h3>[구현방법]</h3>
- myls 프로그램의 결과는 ls와 동일해야하며, 다음option들을 처리해야한다.

--------------------------------------------

$  myls  [option]  [file]

option:   -l  -a  -al 

file : file or directory name

 -----------------------------------------------

예)

$  myls

$ myls -l

$ myls -al  /etc/passwd

$ myls  a.out

$ myls -a  ..

