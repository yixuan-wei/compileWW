grammar MiniJava;
goal:               mainClass (classDeclaration)* EOF;
mainClass:          'class' Identifier '{' 'public' 'static' 'void' 'main' '(' 'String' '[' ']' Identifier ')' '{' statement '}' '}' ;
classDeclaration:   'class' Identifier ('extends' Identifier)? '{'(varDeclaration)* (methodDeclaration)* '}';
varDeclaration:     type Identifier ';';
methodDeclaration:  'public' type Identifier '(' (type Identifier (',' type Identifier)* )? ')' '{' (varDeclaration)* (statement)* 'return' expression ';' '}';

type:       'int' '[' ']'   #intArray
            | 'boolean'     #bool
            | 'int'         #int
            | Identifier    #identify;

statement:  '{' (statement)* '}'                                   #curlyBraces 
            | 'if' '(' expression ')' statement 'else' statement   #ifElse
            | 'while' '(' expression ')' statement                 #while
            | 'System.out.println' '(' expression ')' ';'          #outPrint
            | Identifier '=' expression ';'                        #assignment
            | Identifier '[' expression ']' '=' expression ';'     #arrayAssignment
            ;

expression: 
            '(' expression ')'                                                  #parentheses
            | '!' expression                                                    #negation
            |expression '&&' expression                                         #and
            |expression '<' expression                                          #smaller
            |expression '*' expression                                          #multiplication
            |expression '+' expression                                          #addition
            |expression '-' expression                                          #subtraction    
            | expression '[' expression ']'                                     #readMemory
            | expression '.' 'length'                                           #length
            | expression '.' Identifier '(' (expression (',' expression)*)? ')' #callFunc
            | INT                                                               #integer
            | 'true'                                                            #true
            | 'false'                                                           #false
            | 'this'                                                            #this
            | Identifier                                                        #identifier
            | 'new' 'int' '[' expression ']'                                    #newIntArray
            | 'new' Identifier '(' ')'                                          #newFunc
            ;

Identifier: [a-zA-Z][_0-9a-zA-Z]*;
INT:        ([0]|[1-9][0-9]*);
WhiteSpace: [ \r\n\t]+      ->skip;
COMMENT:    '/*' .*? '*/'   ->skip;
LineComment:'//' ~[\r\n]*   ->skip;