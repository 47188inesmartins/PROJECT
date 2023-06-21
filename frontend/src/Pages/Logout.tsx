import * as React from 'react';
import {Fetch} from "../Utils/useFetch";
import {LoggedInContextCookie} from "../Authentication/Authn";
import {useEffect, useState} from "react";
import {Navigate} from "react-router";


export function Logout() {
    const [redirect, setRedirect] = useState(false);
    const token = React.useContext(LoggedInContextCookie).loggedInState.token;

    fetch("/api/user/logout", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    })
        .then(data => {
            setRedirect(true);
        })
        .catch(error => {
            console.error('An error has occurred:', error);
        });

    useEffect(() => {
        if (redirect) {
            setRedirect(false);
        }
    }, [redirect]);

    if (redirect) {
        window.location.href = '/'
        return <Navigate to='/'/>;
    }

}