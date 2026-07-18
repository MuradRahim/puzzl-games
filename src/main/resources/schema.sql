CREATE TABLE IF NOT EXISTS topics (
    id          BIGSERIAL PRIMARY KEY,
    slug        VARCHAR(100) NOT NULL UNIQUE,
    title       VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL
);

CREATE TABLE IF NOT EXISTS questions (
    id            BIGSERIAL PRIMARY KEY,
    topic_id      BIGINT       NOT NULL REFERENCES topics (id) ON DELETE CASCADE,
    question_text TEXT         NOT NULL,
    sort_order    INT          NOT NULL,
    UNIQUE (topic_id, sort_order)
);

CREATE TABLE IF NOT EXISTS answers (
    id          BIGSERIAL PRIMARY KEY,
    question_id BIGINT       NOT NULL REFERENCES questions (id) ON DELETE CASCADE,
    answer_text TEXT         NOT NULL,
    is_correct  BOOLEAN      NOT NULL DEFAULT FALSE,
    UNIQUE (question_id, answer_text)
);

CREATE INDEX IF NOT EXISTS idx_questions_topic_id ON questions (topic_id);
CREATE INDEX IF NOT EXISTS idx_answers_question_id ON answers (question_id);
