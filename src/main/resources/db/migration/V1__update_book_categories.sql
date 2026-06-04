ALTER TABLE books
DROP CONSTRAINT books_category_check;

ALTER TABLE books
ADD CONSTRAINT books_category_check
CHECK (
    category IN (
         'FICTION',
         'NON_FICTION',
         'SCIENCE',
         'HISTORY',
         'NOVEL_DRAMA'
    )
);
