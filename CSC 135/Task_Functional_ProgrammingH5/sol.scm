;; (a) inc function
(define (inc n)
  (lambda (x) (+ x n)))

;; (b) len function
(define (len lst)
  (define (helper lst count)
    (if (null? lst)
        count
        (helper (cdr lst) (+ count 1))))
  (helper lst 0))

;; (c) maxmin function
(define (maxmin lst)
  (cond ((null? lst) (void))
        ((null? (cdr lst)) (list (car lst) (car lst)))
        (else (let ((sub-maxmin (maxmin (cdr lst))))
                (list (if (> (car lst) (car sub-maxmin)) (car lst) (car sub-maxmin))
                      (if (< (car lst) (cadr sub-maxmin)) (car lst) (cadr sub-maxmin)))))))

;; (d) mem function
(define (mem x lst)
  (cond
   ((null? lst) #f)
   ((equal? x (car lst)) #t)
   (else (mem x (cdr lst)))))

;; (e) ins function
(define (ins x lst)
  (if (mem x lst)
      lst
      (cons x lst)))

;; (f) numT function
(define (numT pred lst)
  (cond ((null? lst) 0)
        ((pred (car lst)) (+ 1 (numT pred (cdr lst))))
        (else (numT pred (cdr lst)))))

;; (g) moreT function
(define (moreT pred lst1 lst2)
  (let ((count1 (numT pred lst1)) (count2 (numT pred lst2)))
    (cond
     ((> count1 count2) 1)
     ((< count1 count2) 2)
     (else 0))))

;; Example Calls
((inc 3) 2)
(len '(2 1))
(maxmin '(4 2 -1 10))
(mem '(1) '(1 4 -2))
(ins 5 '(2 10 -3))
(numT number? '(1 -5 -4 (2 1) 7))
(moreT negative? '(8 -4 3 8) '(7 -3 -2 1 -5))
(moreT even? '(8 -4 3 8) '(6 3 2 1 -4))