import * as React from 'react';
import {
    useState,
    useEffect
} from 'react';
import Cookies from 'js-cookie';

export const HOST = "http://localhost:8000/api"
export type FetchResponse = {
    response:any,
    error:any
}

type Response = {
    status:number,
    body:string
}

export function Fetch(url: string, method: string, requestBody: any = null): FetchResponse{
    const [content, setContent] = useState(undefined);
    const [error, setError] = useState<any>(undefined);
    const token = Cookies.get('name')
    const authorization: { 'Content-Type': string; Authorization?: string; } = (token !== undefined)
        ? {'Content-Type': 'application/json', 'Authorization': `Bearer ${token}`}
        : {'Content-Type': 'application/json'};
    const hostUrl = HOST + url
console.log("auth ====", authorization)
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
                console.log("here",body)
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

export function SimpleFetch(url:string , body: any = null, method:string = 'GET'):Response{
    const token =  Cookies.get('name');
    const [response,setResponse] = useState(undefined)
    const authorization: { 'Content-Type': string; Authorization?: string; } = (token !== undefined)
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
                console.log("RESPOSTA", resp)
                setResponse(resp)
            })
            .catch(error => {
                console.error('Ocorreu um erro:', error);
    });

    return response
}

export function SimpleFetch1(url:string , method:string = 'GET',bodyReq: any = null ){
    const token =  Cookies.get('name');
    const [r,setResponse] = useState(undefined)
    const [status,setStatus] = useState<number|undefined>(undefined)
    const authorization: { 'Content-Type': string; Authorization?: string; } = (token !== "")
        ? {'Content-Type': 'application/json', 'Authorization': `Bearer ${token} `}
        : {'Content-Type': 'application/json'};
    const hostUrl = HOST + url

    useEffect(() => {
    async function doFetch() {
        try{
                const resp = await fetch(hostUrl,{
                    method : method,
                    body : bodyReq? JSON.stringify(bodyReq) : null,
                    headers : authorization
                })
                const body = await resp.json()
                console.log("here",body)
                setStatus(resp.status)
                setResponse(body)
            }catch (error){
                console.log("check error",error)
            }
        }
        doFetch()
    }, [])
    return {status:status,response:r}
}

