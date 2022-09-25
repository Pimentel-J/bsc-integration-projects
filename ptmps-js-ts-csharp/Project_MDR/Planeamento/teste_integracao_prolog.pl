:- use_module(library(http/thread_httpd)).
:- use_module(library(http/http_dispatch)).
:- use_module(library(http/json)).
:- use_module(library(http/http_json)).
:- use_module(library(http/json_convert)).
:- use_module(library(http/http_open)).
:- use_module(library(http/http_client)).

% The predicate server(+Port) starts the server. It simply creates a
% number of Prolog threads and then returns to the toplevel, so you can
% (re-)load code, debug, etc.
server(Port) :-
        http_server(http_dispatch, [port(Port)]).

% Declare a handler, binding an HTTP path to a predicate.
% Here our path is / (the root) and the goal we ll query will be
% say_hi. The third argument is for options
:- http_handler('/', say_hi, []).
:- http_handler('/soma', soma, []).
:- http_handler('/nos', get_nos2, []).

/* The implementation of /. The single argument provides the request
details, which we ignore for now. Our task is to write a CGI-Document:
a number of name: value -pair lines, followed by two newlines, followed
by the document content, The only obligatory header line is the
Content-type: <mime-type> header.
Printing can be done using any Prolog printing predicate, but the
format-family is the most useful. See format/2.   */

say_hi(_Request) :-
        format('Content-type: text/plain~n~n'),
        format('Hello World 2!~n').

soma(Request) :-
        http_read_json_dict(Request, JSONIn, [json_object(term)]),
        format('Content-type: text/plain~n~n'),
        calcular(JSONIn.x, JSONIn.y, Soma),
        format('soma = ~d', Soma).

calcular(X, Y, Soma) :-
        number_string(XN, X),
        number_string(YN, Y),
        Soma is (XN + YN).

get_nos(Request) :-
  http_get('http://localhost:3000/api/nos', Reply, []),
  format('Content-type: text/plain~n~n'),
  lista_nos(Reply).
  %reply_json(Reply).

% Isto vai carregar os nós para a BD (memória)

lista_nos([]):-!.
lista_nos([H|L]) :-
  print_no(H),
  lista_nos(L).

print_no(No) :-
  format('No: abrev="~w", nome="~w" ~n', [No.abreviatura, No.nome] ),
  assertz(no(No.nome,No.abreviatura,No.pontoRendicao,No.estacaoRecolha,No.longitude,No.latitude)).
  % write_term(No, []).

no_get_abreviatura(No, Abrev) :-
  Abrev = No.abreviatura.

get_nos2(Request) :-
  http_open('http://localhost:3000/api/nos', Reply, [request_header('Accept'='application/json')]),
  json_read_dict(Reply, Dict),
  close(Reply),
  format('Content-type: text/plain~n~n'),
  lista_nos(Dict).
  % reply_json(Reply).
