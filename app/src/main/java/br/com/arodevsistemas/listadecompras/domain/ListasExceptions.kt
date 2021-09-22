package br.com.arodevsistemas.listadecompras.domain

sealed class ListasExceptions: Exception() {
    object InvalidDate: ListasExceptions()
    object InvalidDescription: ListasExceptions()
}