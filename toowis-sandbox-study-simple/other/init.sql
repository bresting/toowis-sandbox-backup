/*--------------------------------------------------------------------------------------------------------------------*/
/* 공통코드 샘플링                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
CREATE TABLE public.common_code (
      group_code varchar(40) NOT NULL
    , code varchar(40) NOT NULL
    , code_name varchar(200) NULL
    , code_desc varchar(500) NULL
    , CONSTRAINT common_code_pk PRIMARY KEY (code)
);

CREATE INDEX common_code_idx_01 ON public.common_code USING btree (group_code, code);

COMMENT ON COLUMN public.common_code.group_code IS '그룹코드';
COMMENT ON COLUMN public.common_code.code IS '코드';
COMMENT ON COLUMN public.common_code.code_name IS '코드명';
COMMENT ON COLUMN public.common_code.code_desc IS '코드설명';

INSERT INTO public.common_code (group_code, code, code_name, code_desc)
VALUES ( 'DEV_CODE', 'ECLIPSE', '이클립스', '이클립스_개발툴' )
     , ( 'DEV_CODE', 'INTELLIJ', '인텔리J', '인텔리J_개발툴' )
     , ( 'DEV_CODE', 'VSCODE', '비주얼 스튜디오 코드', '비주얼 스튜디오 코드_개발툴' )
