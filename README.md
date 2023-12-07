<br>

<div align="center">

<img width="224" alt="오늘은 내차례" src="https://github.com/MINI-TEAM8-FC/BE_my_turn/assets/43840220/b1d3288f-b22c-4fc3-93a3-33744871fd95">

# 오늘은 내 차례! (My Turn) [🔗](https://my-turn.netlify.app)


</div>

<br>

## 프로젝트 소개
- 사유는 필요없다! 당당히 손들고 오늘은 내 차례라고 말할 수 있는 연차 & 당직 관리 서비스입니다. 
- 본인의 연차와 당직을 신청 및 관리하고, 관리자가 이를 승인 및 반려가 가능합니다.
- 기간 : ```2023.07.24 ~ 2023.08.11```

<br><br>

## 프로젝트에 사용한 기술 스택
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-squre&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat-squre&logo=springsecurity&logoColor=white"> 
<img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=flat-squre&logo=spring&logoColor=white"> 

<img src="https://img.shields.io/badge/Java 11-FF160B?style=flat-squre&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/Gradle-02303A?style=flat-squre&logo=gradle&logoColor=white"> 
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-squre&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/Amazon AWS-41454A?style=flat-squre&logo=amazonaws&logoColor=white">

<br><br>


## 시스템 아키텍처 
<img width="823" alt="image" src="https://github.com/Fastcampus-Final-Team3/jober-backend/assets/43840220/f8053e00-9971-4782-9c44-d1ae1dbf083f">

<br><br>

## ERD
<img width="600" alt="image" src="https://github.com/Fastcampus-Final-Team3/jober-backend/assets/43840220/f9be2bda-b438-4e80-af63-02861e7eacf5">

<br><br>

## API 명세서
<table border="0">
	<th>기능</th>
	<th>URL</th>
  <th>Method</th>
	<tr>
	    <td>회원가입</td>
	    <td>/main</td>
      <td>GET</td>
	</tr>
	<tr>
	    <td>로그인</td>
	    <td>/user/join</td>
      <td>POST</td>
	</tr>
  <tr>
	    <td>이메일중복체크</td>
	    <td>/user/email</td>
      <td>POST</td>
	</tr>
  <tr>
	    <td>로그아웃</td>
	    <td>/user/logout</td>
      <td>POST</td>
	</tr>
  <tr>
	    <td>마이페이지</td>
	    <td>/user/myinfo</td>
      <td>GET</td>
  <tr>
	    <td>내 정보 수정</td>
	    <td>/user/myinfo</td>
      <td>PUT</td>
	</tr>
  <tr>
	    <td>연차/당직 신청</td>
	    <td>/user/event</td>
      <td>POST</td>
	</tr>
  <tr>
	    <td>연차/당직 신청 취소</td>
	    <td>/user/event/{id}</td>
      <td>DELETE</td>
	</tr>
  <tr>
	    <td>연차/당직 신청 현황</td>
	    <td>/user/event</td>
      <td>GET</td>
	</tr>
  <tr>
	    <td>모든 유저 연차/당직 리스트</td>
	    <td>/user/events</td>
      <td>GET</td>
	</tr>
  <tr>
	    <td>연차 승인</td>
	    <td>/admin/leave/approval</td>
      <td>POST</td>
	</tr>
  <tr>
	    <td>당직 승인</td>
	    <td>/admin/duty/approval</td>
      <td>POST</td>
	</tr>
  <tr>
	    <td>연차/당직 신청 리스트</td>
	    <td>/admin/event/request</td>
      <td>GET</td>
	</tr>
</table>

<br><br>

## 프로젝트 시현

### 로그인 페이지

![로그인](https://github.com/MINI-TEAM8-FC/BE_my_turn/assets/43840220/8f2b31b0-0198-4a55-b357-9366dca4aa40)

<br>

### 회원 정보 수정

![회원정보수정](https://github.com/MINI-TEAM8-FC/BE_my_turn/assets/43840220/7a62bc52-ef42-4ba8-be4c-8cd256ab60a8)

<br>

### 관리자 연차 & 당직 승인 및 반려 

![관리자페이지](https://github.com/MINI-TEAM8-FC/BE_my_turn/assets/43840220/02910ea3-ad39-4b5a-bb2c-9694840c6a6f)


<br><br>


## 백엔드 팀원 역할

 <table>
    <tbody>
      <tr>
        <td align="center"><a href="https://github.com/james-taeil">
          <img src="https://avatars.githubusercontent.com/u/71359732?v=4" width="200px;" alt=""/><br /><sub><b>김태일</b></sub></a><br />
        </td>
        <td align="center"><a href="https://github.com/sohn919">
          <img src="https://avatars.githubusercontent.com/u/84082544?v=4" width="200px;" alt=""/><br /><sub><b>손영준</b></sub></a><br />
        </td>
        <td align="center"><a href="https://github.com/miyounlee">
          <img src="https://avatars.githubusercontent.com/u/43840220?v=4" width="200px;" alt=""/><br /><sub><b>이미연</b></sub></a><br />
        </td>
        <td align="center"><a href="https://github.com/MebukiYamashi">
          <img src="https://avatars.githubusercontent.com/u/91310994?v=4" width="200px;" alt=""/><br /><sub><b>이성민</b></sub></a><br />
        </td>
      </tr>
      <tr>
        <td>
          회원정보 수정,<br />회원정보 조회,<br />프로젝트 초기세팅
        </td>
        <td>
          회원가입,<br />로그인,<br />JWT
        </td>
        <td>
          연차 / 당직 신청 및 취소,<br />연차 / 당직 조회,<br />서버 배포
        </td>
        <td>
          관리자 연차 / 당직 승인,<br />관리자 연차 / 당직 조회
        </td>
      </tr>
    </tbody>
  </table>
<br><br>

