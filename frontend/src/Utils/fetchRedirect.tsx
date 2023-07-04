import React, {useEffect, useState} from "react";
import {LoggedInContextCookie} from "../Authentication/Authn";

const HOST = "http://localhost:8000/api"

export function simpleFetch(url:string,requestBody:any = null,method:string='GET') {
    const token = React.useContext(LoggedInContextCookie).loggedInState.token
    const authorization: { 'Content-Type': string; Authorization?: string; } = (token !== "")
        ? {'Content-Type': 'application/json', 'Authorization': `Bearer ${token}`}
        : {'Content-Type': 'application/json'}
    fetch(HOST+url,{
        method: method,
        body : requestBody? JSON.stringify(requestBody) : null,
        headers: authorization
    })
        .then(response => {
            console.error('Resposta ao pedido:', response);
        })
        .catch(error => {
            console.error('Ocorreu um erro:', error);
        });
}




