import React, {useEffect, useState} from "react";
import axios from 'axios';
import {LoggedInContextCookie} from "./Authentication/Authn";
const HOST = "http://localhost:8080"

export type FetchResponse = {
    response:any,
    error:any
}


export function Fetch(url: string, method: string, requestBody: any = null): FetchResponse{
    const [content, setContent] = useState(undefined);
    const [error, setError] = useState<any>(undefined);
    const token = React.useContext(LoggedInContextCookie).loggedInState.token;
    const authorization: { 'Content-Type': string; Authorization?: string; } = (token !== "")
        ? {'Content-Type': 'application/json', 'Authorization': `Bearer ${token} `}
        : {'Content-Type': 'application/json'};

    const hostUrl = HOST + url

    useEffect(() => {
        let cancelled = false
        console.log("dentro do fetch")
        async function doFetch() {
            try{
                console.log("RES",requestBody)
                console.log("here")
                const resp = await fetch(hostUrl,{
                    method : method,
                    body : requestBody? JSON.stringify(requestBody) : null,
                    headers : authorization
                })
                if(resp.status === 401){
                    console.log("here")
                    const t = {response:undefined,error:resp.status}
                    console.log(t)
                    return t
                }
                const body = await resp.json()


                console.log("BODY", body)
                if (!cancelled) {
                    console.log(body)
                    setContent(body)
                }
            }catch (error){
                setError(error)
            }
        }
        setContent(undefined)
        doFetch()
        return () => {
            cancelled = true
        }
    }, [url, method, requestBody])
    return {response:content,error:error}
}


