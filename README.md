# 동시성 문제 해결 실습 과정
- [Velog 글](https://velog.io/@baekgwa/%EB%8F%99%EC%8B%9C%EC%84%B1-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0-%ED%95%B4%EB%B3%B4%EA%B8%B0.-Feat-DB-Lock)

## 설명
- ItemServiceImplV1 : 동시성 문제 발견 목적 코드
- ItemServiceImplV2 : 동시성 문제 해결을 위한 Application 단에서의 Java 동기화 코드 적용. (synchronized) (실패)
- ItemServiceImplV3 : 동시성 문제 해결을 위한 Application 단에서의 Java 동기화 코드 적용. (ReentrantLock) (실패)
- ItemServiceImplV4 : DB 비관적 락 적용. PESSIMISTIC
- ItemServiceImplV5 : DB 낙관적 락 적용. OPTIMISTIC
