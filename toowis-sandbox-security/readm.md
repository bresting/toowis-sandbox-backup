
### OAuth server 등록 방법(google, facebook, naver, kakao)
[Spring Boot] OAuth2 소셜 로그인 가이드 (구글, 페이스북, 네이버, 카카오)
- <https://deeplify.dev/back-end/spring/oauth2-social-login>
    - <https://github.com/callicoder/spring-boot-react-oauth2-social-login-demo>

provide 직접 구현  
- <https://as-you-say.tistory.com/107>
- <https://s-yeonjuu.tistory.com/2>

### OAuth2
참조  
- <https://docs.spring.io/spring-security/site/docs/5.2.12.RELEASE/reference/html/oauth2.html#oauth2login-sample-redirect-uri>
- <https://docs.spring.io/spring-security/site/docs/5.2.12.RELEASE/reference/html/oauth2.html#oauth2login-javaconfig-wo-boot>
- <https://www.codejava.net/frameworks/spring-boot/oauth2-login-with-google-example>

```
spring이 제공하는 대상
org.springframework.security.config.oauth2.client.CommonOAuth2Provider
GOOGLE
GITHUB
FACEBOOK
OKTA

종류    루트 URL                 리다이렉트 URL              로그인 URL
Google   http://localhost:8080   /login/oauth2/code/google   /oauth2/authorization/google
Github   http://localhost:8080   /login/oauth2/code/github   /oauth2/authorization/github
Facebook http://localhost:8080   /login/oauth2/code/facebook /oauth2/authorization/facebook
Kakao    http://localhost:8080   /login/oauth2/code/kakao    /oauth2/authorization/kakao
Naver    http://localhost:8080   /login/oauth2/code/naver    /oauth2/authorization/naver

네이버 로그인 API에서 사용하는 주요 요청 URL과 메서드, 응답 형식
https://developers.naver.com/docs/common/openapiguide/apilist.md#%EB%84%A4%EC%9D%B4%EB%B2%84-%EB%A1%9C%EA%B7%B8%EC%9D%B8

요청URL                                  | 메서드   | 응답형식 | 설명
https://nid.naver.com/oauth2.0/authorize | GET/POST | -        | 네이버 로그인 인증을 요청합니다.
https://nid.naver.com/oauth2.0/token     | GET/POST | JSON     | 접근 토큰의 발급, 갱신, 삭제를 요청합니다.
https://openapi.naver.com/v1/nid/me      | GET      | JSON     | 네이버 회원의 프로필을 조회합니다.
```

### 스키마

```
/org/springframework/security/oauth2/client/oauth2-client-schema-postgres.sql

create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);

create table oauth_client_token (
  token_id VARCHAR(256),
  token bytea,
  authentication_id VARCHAR(256),
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

create table oauth_access_token (
  token_id VARCHAR(256),
  token bytea,
  authentication_id VARCHAR(256),
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication bytea,
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token bytea,
  authentication bytea
);

create table oauth_code (
  code VARCHAR(256), authentication bytea
);

create table oauth_approvals (
  userId VARCHAR(256),
  clientId VARCHAR(256),
  scope VARCHAR(256),
  status VARCHAR(10),
  expiresAt TIMESTAMP,
  lastModifiedAt TIMESTAMP
);
```

### OAuth2 승인 방식의 종류
참조
- <https://cheese10yun.github.io/spring-oauth2-provider/></br>
 └ <https://github.com/cheese10yun/springboot-oauth2>


- Authorization Code Grant Type : 권한 부여 코드 승인 타입</br>
  클라이언트가 다른 사용자 대신 특정 리소스에 접근을 요청할 때 사용됩니다. 리스소 접근을 위한 사용자 명과 비밀번호, 권한 서버에 요청해서 받은 권한 코드를 함께 활용하여 리소스에 대한 엑세스 토큰을 받는 방식입니다.


- Implicit Grant Type : 암시적 승인</br>
  권한 부여 코드 승인 타입과 다르게 권한 코드 교환 단계 없이 엑세스 토큰을 즉시 반환받아 이를 인증에 이용하는 방식입니다.


- Resource Owner Password Credentials Grant Type : 리소스 소유자 암호 자격 증명 타입</br>
  클라이언트가 암호를 사용하여 엑세스 토큰에 대한 사용자의 자격 증명을 교환하는 때입니다.


- Client Credentials Grant Type : 클라이언트 자격 증명 타입</br>
  클라이언트가 컨텍스트 외부에서 액세스 토큰을 얻어 특정 리소스에 접근을 요청할 때 사용하는 방식입니다.
