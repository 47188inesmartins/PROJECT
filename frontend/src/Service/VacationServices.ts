import {Fetch} from "../Utils/useFetch";


export namespace VacationService {

        export function addVacation(cid,vacationDate){
            const resp = Fetch(`/company/${cid}/vacation`, "POST", vacationDate);
            if(resp.response){
                console.log("RESPOONNN",resp.response)
                if(resp.response.status === 201){
                    alert('Vacations added')
                    window.location.href = `/company/${cid}/managing/vacations`;
                }else if(resp.response.status === 400){
                    alert('Something went wrong')
                }else if(resp.response.status === 401) {
                    window.location.href = `/signup`;
                }else if(resp.response.status === 403) {
                    alert('You can´t access this resource')
                }
            }

        }
}
