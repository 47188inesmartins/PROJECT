import {Fetch} from "../Utils/useFetch";
import Cookies from 'js-cookie';


export namespace VacationService {
    export function addVacation(cid,vacationDate){
        const token =  Cookies.get('name');
        fetch(`/api/company/${cid}/vacation`,{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token} `
            },
            body: JSON.stringify(vacationDate)
        }).then(response => {
                if(response.status === 201){
                    alert('Vacations added')
                    window.location.href = `/company/${cid}/managing/vacations`;
                }else if(response.status === 400){
                    alert('Something went wrong')
                }else if(response.status === 401) {
                    window.location.href = `/signup`;
                }else if(response.status === 403) {
                    alert('You cant access this resource')
                }
            }
        )
    }
}