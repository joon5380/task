# 삼쩜삼 백엔드 엔지니어 채용 과제

### 최대준 / joon5380@gmail.com 
## 1. 구현 내역
- ### 공통내역
> 1. API 응답 객체는 [ApiResponse.java](src%2Fmain%2Fjava%2Fcom%2Ftask%2Ftaskproject%2Fdto%2FApiResponse.java)로 일괄 처리 
> 2. 운영 중 발생하는 예외는 [ExceptionResponseHandler.java](src%2Fmain%2Fjava%2Fcom%2Ftask%2Ftaskproject%2Fhandler%2FExceptionResponseHandler.java)로 일괄 처리
> 3. 필수, 추가 API 전체 Swagger 활용한 테스트 가능 : http://localhost:8080/3o3/swagger.html

- ### 필수기능 : 회원가입 API
> 1. JPA를 통해 신규 회원 정보 H2 DB 저장
> 2. PDF에 기제된 사용자만 회원가입이 가능하도록 검증
> 3. API 요청 필수 값 여부 검증
> 4. 비밀번호는 단방향, 주민등록번호는 양방향 암호화 처리 (한국인터넷진흥원 KISA_SEED_CBC 알고리즘 활용)
> 5. 정상처리 시 사용자 정보(아이디, 이름, 주민등록번호) 반환
> 6. [AuthServiceTest.java](src%2Ftest%2Fjava%2Fcom%2Ftask%2Ftaskproject%2FAuthServiceTest.java) : Junit 테스트 코드 작성

- ### 필수기능 : 로그인 API
> 1. 신규 JWT 토큰 발급
> 2. 토큰 기간 만료, 토큰 변조 등 검증
> 3. API 요청 필수 값 여부 검증
> 4. Spring Security를 활용한 filter, 권한, 검증 등 처리
> 5. 정상처리 시 Access Token 값 반환
> 6. [JwtResponseTest.java](src%2Ftest%2Fjava%2Fcom%2Ftask%2Ftaskproject%2FJwtResponseTest.java) : Junit 테스트 코드 작성

- ### 필수기능 : 스크래핑 API
> 1. JWT 토큰 검증 처리
> 2. 로그인한 사용자의 이름과 주민등록번호를 활용하여 외부 스크래핑 API 요청
> 3. 외부 스크래핑 API 응답시간 30초 초과 시 응답오류 처리
> 4. 외부 스크래핑 API 정보인 URL, X-API-KEY 값은 별도의 [szs.yml](src%2Fmain%2Fresources%2Fszs.yml) 파일로 관리
> 5. 스크래핑 데이터 파싱 처리하여 MEMBERINCMINFO, MEMBERINCMINFOPENSION, MEMBERINCMINFOCARD DB 테이블에 저장
> 6. 외부통신, Parsing 작업, 복호화 작업 시 발생할 수 있는 예외처리
> 7. 정상처리 시 사용자 ID 값 반환

- ### 필수기능 : 결정세액 조회 API
> 1. JWT 토큰 검증 처리 
> 2. 로그인한 사용자 ID를 PK로 하여 DB 테이블 전체 데이터 조회
> 3. 조회된 데이터 중 금액으로 계산이 필요한 부분 실수형 데이터 반올림 처리 
> 4. 세액 계산식에 맞춰 계산 후 '결정세액' 값 반환

- ### 추가기능 : 회원정보 RUD API
> 1. 회원 로그인 이후 사용가능한 API
> 2. URL : http://localhost:8080/member
>> 1) GET 방식 요청 시 회원 정보 조회
>> 2) PUT 방식 요청 시 회원 정보 수정
>> 3) DELETE 방식 요청 시 회원 탈퇴
> 3. [MemberServiceTest.java](src%2Ftest%2Fjava%2Fcom%2Ftask%2Ftaskproject%2FMemberServiceTest.java) : Junit 테스트 코드 작성

- ### 추가기능 : 관리자용 API
> 1. 관리자 로그인 이후 사용가능한 API
> 2. [MemberInitialize.java](src%2Fmain%2Fjava%2Fcom%2Ftask%2Ftaskproject%2FMemberInitialize.java)에서 임시 관리자 자동 생성
>> 1) URL : http://localhost:8080/admin/user-list 회원 목록 조회 API
>> 2) URL : http://localhost:8080/admin/admin-list 관리자 목록 조회 API

## 2. 구현 방법
> 1. DB 테이블 설계, 프로젝트 구조, JWT Spring Security 활용, Swagger 활용을 위한 구성
> 2. 회원가입 > 로그인 > 스크래핑 > 결정세액 조회 순으로 API 개발
> 3. 요청사항에 맞춰서 개발하되 디테일한 부분은 경험을 바탕으로 재사용성, 확장성 등을 고려하여 작성

## 3. 테스트
> 1. Swagger 및 TestCode를 토대로 테스트 진행   
> 2. 테스트 케이스의 맞춰 요청값을 변경하였고 최종적으로 DB 입력값 확인
> 3. 테스트 순서
>> - 회원가입 > 로그인 > 스크래핑 > 결정세액 순으로 진행
>> - 추가 API 같은 경우는 일반 사용자 로그인, 관리자 로그인을 통해 각각 테스트 가능
>> - Swagger 테스트 시에는 회원가입 후 로그인 하여 발급된 토큰 값을 login하여 얻어온 token값을 우측 상단에 위치한 'Authorize'에 넣으면 자동으로 header 값에 고정
>> - Test Code는 순서 상관없이 테스트 가능