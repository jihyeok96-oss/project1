-- ============================================================
-- Notice Entity 기반 SQL DML (Data Manipulation Language) 정리
-- Table Name: notices
-- ============================================================

-- [참고] 테이블 생성 DDL (Data Definition Language)
/*
CREATE TABLE notices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    author VARCHAR(50) NOT NULL DEFAULT 'admin',
    hits BIGINT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
*/


-- ============================================================
-- 1. INSERT (데이터 삽입)
-- ============================================================

-- 1-1. 필수 필드만 작성 (author, hits, created_at 은 기본값 적용)
INSERT INTO notices (title, content) 
VALUES (
    '첫 번째 공지사항입니다.', 
    '서비스 이용 안내 관련 내용입니다.'
);

-- 1-2. 전체 필드를 지정하여 작성
INSERT INTO notices (title, content, author, hits, created_at) 
VALUES (
    '시스템 정기 점검 안내', 
    '서비스 안정화를 위한 정기 점검이 진행될 예정입니다.', 
    'sysadmin', 
    0, 
    NOW()
);

-- 1-3. 테스트용 다중 데이터 등록 (Bulk Insert)
INSERT INTO notices (title, content, author) 
VALUES 
    ('이벤트 당첨자 발표', '축하합니다! 이벤트 당첨자 목록입니다.', 'admin'),
    ('개인정보처리방침 개정 안내', '개인정보처리방침이 일부 변경됩니다.', 'operator'),
    ('신규 기능 추가 안내', '새로운 기능이 업데이터 되었습니다.', 'admin');


-- ============================================================
-- 2. SELECT (데이터 조회)
-- ============================================================

-- 2-1. 전체 공지사항 목록 조회 (최신순 정렬)
SELECT id, title, author, hits, created_at 
FROM notices 
ORDER BY id DESC;

-- 2-2. 특정 공지사항 상세 조회 (Primary Key 이용)
SELECT id, title, content, author, hits, created_at 
FROM notices 
WHERE id = 1;

-- 2-3. 제목 또는 내용으로 키워드 검색
SELECT id, title, author, hits, created_at 
FROM notices 
WHERE title LIKE '%점검%' OR content LIKE '%점검%'
ORDER BY id DESC;

-- 2-4. 작성자별 공지사항 조회
SELECT id, title, hits, created_at 
FROM notices 
WHERE author = 'admin'
ORDER BY id DESC;

-- 2-5. 페이징 처리 (첫 번째 페이지: 10개 조회)
SELECT id, title, author, hits, created_at 
FROM notices 
ORDER BY id DESC 
LIMIT 10 OFFSET 0;


-- ============================================================
-- 3. UPDATE (데이터 수정)
-- ============================================================

-- 3-1. 공지사항 제목 및 내용 수정
UPDATE notices 
SET title = '[수정] 시스템 정기 점검 안내', 
    content = '점검 시간이 02:00 ~ 04:00 로 변경되었습니다.' 
WHERE id = 2;

-- 3-2. 조회수(hits) 1 증가 (상세 보기 시 호출)
UPDATE notices 
SET hits = hits + 1 
WHERE id = 1;

-- 3-3. 작성자 변경
UPDATE notices 
SET author = 'superadmin' 
WHERE author = 'admin';


-- ============================================================
-- 4. DELETE (데이터 삭제)
-- ============================================================

-- 4-1. 특정 공지사항 삭제
DELETE FROM notices 
WHERE id = 1;

-- 4-2. 특정 작성자의 글 일괄 삭제
DELETE FROM notices 
WHERE author = 'test_user';