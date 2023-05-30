import React from "react";
import {Navigate, useLocation} from "react-router";
import {LoggedInContextCookie} from "./Authn";
import {Fetch} from "../useFetch";


/**
 * Required Auth client
 * @param children
 * @constructor
 */
export function RequireAuthn({ children }: { children: React.ReactNode }): React.ReactElement {
    const tokenUser = React.useContext(LoggedInContextCookie).loggedInState.token
    const location = useLocation()
    console.log(`currentToken = ${tokenUser}`)
    if (tokenUser) {
        return <>{children}</>
    } else {
        console.log("redirecting to login")
        return <Navigate to="/login" state={{source: location.pathname}} replace={true}/>
    }
}

export function RequireRole({ children }: { children: React.ReactNode }): React.ReactElement {
    //const tokenUser = React.useContext(LoggedInContextCookie).loggedInState.token
    const location = useLocation()
    const response = Fetch("/role","GET")
    if (response) {
        if(response.response === "MANAGER")
            return <>{children}</>
        else
            return (<></>)
        //return <Navigate to="/" state={{source: location.pathname}} replace={true}/>
    }
    else {
        return (<></>)
    }
}



