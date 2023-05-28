import React, {useEffect, useState} from "react";
const HOST = "http://localhost:8080"

export type FetchResponse = {
    response:any,
    error:any
}


export function Fetch(url: string, method: string, requestBody: any = null/*, token: string*/): FetchResponse{
    const [content, setContent] = useState(undefined)



    console.log(requestBody)
    const authorization = {
        'Content-Type': 'application/json',
        'Authorization' : 'Bearer 40e196c0-d2e0-435d-9d20-38ef1ff7bffd'
    }
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
                const body = await resp.json()
                console.log("BODY", body)
                if (!cancelled) {
                    console.log(body)
                    setContent(body)
                }
            }catch (error){
                console.log("error = ", error)
            }
        }
        setContent(undefined)
        doFetch()
        return () => {
            cancelled = true
        }
    }, [url,method,requestBody])
    return {response:content, error:undefined}
}