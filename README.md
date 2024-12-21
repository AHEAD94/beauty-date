# beauty-date

- 도메인분석및SW설계 과목의 팀 프로젝트 결과물
- 컨셉 이해를 돕기 위해 제작된 안드로이드 애플리케이션
- 실시간 DB 기반의 화장품 사용기한 관리 및 알림 서비스 애플리케이션

## 1. 앱 설명  

- BeautyDate는 화장품 정보를 등록하고, 제품 '유통기한'과 제품 카테고리의 '개봉 후 사용기한'을 조합한 사용안전기한을 보여주는 애플리케이션이다.
- 애플리케이션에 등록된 제품은 데이터베이스에 저장되고 이를 리스트뷰로 확인할 수 있다.  
- 제품을 등록하면 같은 제품을 등록한 사용자들이 공통적으로 가장 많이 사용하는 제품을 추천하는 기능이 포함된다.  
- Firebase의 실시간 데이터베이스를 연동하여 시스템의 요구사항을 만족하도록 구성함.  

## 2. 빌드 방식  

- Android Studio 3.6.2  
- Firebase 실시간 데이터베이스  
  
## 3. 에뮬레이터 환경  

- 대상 디바이스: Pixel  
- 시스템 이미지: Android Pie, API 28, x86, Android 9.0  

## 4. 앱 인스트럭션  
  
- 앱 실행 후, 화장품 등록을 선택하여 제품명, 유통기한, 개봉일자를 입력 (제품명은 name에 해당하는 입력이 전달되어야 올바른 사용안전기한이 계산됨)  
- 입력 후 다음 버튼을 누르면 등록완료 화면에 계산된 사용만료일과 등록한 제품을 사용하는 사람들이 가장 많이 사용하는 제품 하나를 추천해 줌 (사용만료일은 제품의 카테고리에 해당하는 개봉 후의 화장품 수명을 개봉일자에 더하여, 이 일자가 유통기한을 초과한 경우 유통기한을 보여주며, 초과하지 않은 경우 계산된 날짜를 보여줌)
