import React, {useEffect, useState} from "react";
import axios from 'axios';
const HOST = "http://localhost:8080"

export type FetchResponse = {
    response:any,
    error:any
}


export function Fetch(url: string, method: string, requestBody: any = null/*, token: string*/): FetchResponse{
    const [content, setContent] = useState(undefined)
    const [error, setError] = useState<any>(undefined);
    const authorization = {
        'Content-Type': 'application/json',
       'Authorization' : 'Bearer aa714939-b9a3-4718-be6f-3fbf00adb241'
    }
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


