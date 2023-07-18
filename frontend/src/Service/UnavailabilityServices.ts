import {convertMinutesToHHMMSS} from "../Utils/formater";
import Cookies from 'js-cookie';

export namespace UnavailabilityServices{

    export function createUnavailability(cid,unavail){
        const token =  Cookies.get('name');
        fetch(`/api/unavailability/company/${cid}`,{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token} `
            },
            body: JSON.stringify(unavail)
        }).then(response =>{
                if(response.status == 400){
                    window.alert("An error as occurred!")
                    window.location.href =`/company/${cid}/managing/unavailability`
                }else if(response.status == 201) {
                    window.alert("You added an unavailability!")
                    window.location.href = `/company/${cid}/profile`;
                }
            }
        )
    }








}