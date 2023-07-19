import * as React from 'react';
import {Fetch} from "../Utils/useFetch";
import {LoggedInContextCookie} from "../views/Authentication/Authn";
import {useEffect, useState} from "react";
import {Navigate} from "react-router";
import Cookies from 'js-cookie';

export function Logout() {
    const [redirect, setRedirect] = useState(false);
    //const token = React.useContext(LoggedInContextCookie).loggedInState.token;
    const token = Cookies.get('name');
    fetch("/api/user/logout", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    })
        .then(data => {
            Cookies.remove('name');
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