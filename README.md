# 스프링부트를 이용한 To Do List 만들기
---

### day1

- 도메인 생성
- mysql과 연동하여 임의로 200개의 데이터 넣기
- [bootstrap](http://bootstrapk.com/)을 사용한 css적용

**day1 최종화면**
![day1](/images/day1.png)

### day2

- 등록 구현
- 개별 삭제 구현
- 전체 삭제 구현

**day2 최종화면**
![day2](/images/day2.png)

### day3

- 수정 구현
- 완료여부 구현
- css 일부 적용

**day3 최종화면**
![day3](/images/day3.png)

### day4

- 회원가입 구현
- 로그인 구현
- TodoListController로 합침(SaveController, DeleteController)
- User 생성
- UserController 생성
- UserRepository 생성
- 중복 id 생성 방지
- user가 자신의 tdlList를 보게 함
- logout 기능 구현


**day4 최종화면**

- **login**
![day4_login](/images/day4_login.png)

- **register**
![day4_register](/images/day4_register.png)

### day5

- User, ToDoList를 @OneToMany(mappedBy = "user"), @ManyToOne로 양방향 관계 설정
- User의 add1 메소드로 User와 ToDoList 객체 간의 양방향 관계 설정

**day5 최종화면**

- **test1**
![day5-test1](/images/day5-test1.png)

- **test1 - console**
![day5-test1Console](/images/day5-test1Console.png)

- **test2**
![day5-test1](/images/day5-test2.png)

- **test2 - console**
![day5-test1Console](/images/day5-test2Console.png)

### day6

- `spring security` 적용
	- `login.html`에서 name = "id" 적용
	- `UserDetailsService` 인터페이스의 `loadUserByUsername` 메소드 구현
	- 현재 Session 사용자의 정보를 받아오는 `currentUserId` 구현
	- [참조](http://chomman.github.io/blog/spring%20framework/spring-security%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%98%EC%97%AC-%EC%82%AC%EC%9A%A9%EC%9E%90%EC%9D%98-%EC%A0%95%EB%B3%B4%EB%A5%BC-%EC%B0%BE%EB%8A%94-%EB%B0%A9%EB%B2%95/)
	- id 중복검사 적용, 중복검사를 하지않으면 `login`화면으로 가지 못하게함