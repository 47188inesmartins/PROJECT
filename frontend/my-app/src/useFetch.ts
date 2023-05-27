import React, {useEffect, useState} from "react";
import axios from 'axios';
const HOST = "http://localhost:8080"

export type FetchResponse = {
    response:any,
    error:any
}
export function delay(delayInMs: number) {
    return new Promise((resolve, reject) => {
        setTimeout(() => resolve(undefined), delayInMs)
    })
}

export function Fetch(url: string, method: string, requestBody: any = null): FetchResponse{
    const [content, setContent] = useState(undefined)

    const authorization = {'Content-Type': 'application/json'}
    const hostUrl = "http://localhost:8080" + url

    useEffect(() => {
        let cancelled = false
        async function doFetch() {
            try{
                console.log("RES",requestBody)
                const resp = await fetch(hostUrl,{
                    method : method,
                    body : requestBody,
                    headers : authorization
                })
                console.log("headers",await resp.headers)
                const body = await resp.json()
                console.log("BODY", body)
                if (!cancelled) {
                    console.log(body)
                    setContent(body)
                }
            }catch (error){
            }
        }
        setContent(undefined)
        doFetch()
        return () => {
            cancelled = true
        }
    }, [url,method,requestBody])
    return {response:content,error:undefined}
}