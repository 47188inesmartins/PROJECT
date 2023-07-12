import * as React from 'react';
import {
    useState,
    useEffect
} from 'react';
import {LoggedInContextCookie} from "../Authentication/Authn";
import Cookies from 'js-cookie';

export const HOST = "http://localhost:8000/api"
export type FetchResponse = {
    response:any,
    error:any
}

export function Fetch(url: string, method: string, requestBody: any = null): FetchResponse{
    const [content, setContent] = useState(undefined);
    const [error, setError] = useState<any>(undefined);
    const token = React.useContext(LoggedInContextCookie).loggedInState.token;
    const authorization: { 'Content-Type': string; Authorization?: string; } = (token !== "")
        ? {'Content-Type': 'application/json', 'Authorization': `Bearer ${token}`}
        : {'Content-Type': 'application/json'};
    const hostUrl = HOST + url

    useEffect(() => {
        let cancelled = false
        async function doFetch() {
            try{
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

export function SimpleFetch(url:string , body: any = null, method:string = 'GET'){
    const token =  Cookies.get('name');
    const [response,setResponse] = useState(undefined)
    const authorization: { 'Content-Type': string; Authorization?: string; } = (token !== "")
        ? {'Content-Type': 'application/json', 'Authorization': `Bearer ${token} `}
        : {'Content-Type': 'application/json'};
    const hostUrl = HOST + url

    fetch(hostUrl, {
            method: method,
            body: JSON.stringify(body),
            headers: authorization
        })
            .then(response =>{
                const resp = response.json()
                setResponse(resp)
            })
            .catch(error => {
                console.error('Ocorreu um erro:', error);
    });

    return response
}