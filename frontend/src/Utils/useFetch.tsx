import * as React from 'react';
import {
    useState,
    useEffect
} from 'react';
import {LoggedInContextCookie} from "../Authentication/Authn";
import {Navigate} from "react-router";
const HOST = "http://localhost:8000/api"

export type FetchResponse = {
    response:any,
    error:any
}



export function Fetch(url: string, method: string, requestBody: any = null): FetchResponse{
    const [content, setContent] = useState(undefined);
    const [error, setError] = useState<any>(undefined);
    const token = React.useContext(LoggedInContextCookie).loggedInState.token;
    console.log("TOKEN = ", token)
    const authorization: { 'Content-Type': string; Authorization?: string; } = (token !== "")
        ? {'Content-Type': 'application/json', 'Authorization': `Bearer ${token} `}
        : {'Content-Type': 'application/json'};
    const hostUrl = HOST + url

    useEffect(() => {
        let cancelled = false
        async function doFetch() {
            try{
                console.log("RES",requestBody)
                console.log("here")
                const resp = await fetch(hostUrl,{
                    method : method,
                    body : requestBody? JSON.stringify(requestBody) : null,
                    headers : authorization
                })
                console.log("Fetch do body",resp)
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
    }, [url,method,requestBody])
    return {response:content,error:error}
}

export function SimpleFetch(url:string , body = null, method:string = 'GET'){
    const token = React.useContext(LoggedInContextCookie).loggedInState.token;
    console.log("TOKEN = ", token)
    const authorization: { 'Content-Type': string; Authorization?: string; } = (token !== "")
        ? {'Content-Type': 'application/json', 'Authorization': `Bearer ${token} `}
        : {'Content-Type': 'application/json'};
    const hostUrl = HOST + url

    fetch(hostUrl, {
            method: method,
            body: JSON.stringify(body),
            headers: authorization
        })
            .then(response => response.json())
            .catch(error => {
                console.error('Ocorreu um erro:', error);
    });

}