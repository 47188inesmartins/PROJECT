import {Fetch} from "../Utils/useFetch";


export namespace VacationService {

        export function addVacation(cid,vacationDate){
            const resp = Fetch(`/company/${cid}/vacation`, "POST", vacationDate);
            if(resp.response.status === 201){
                alert('Vacations added')
            }else if(resp.response.status === 400){
                alert('Something went wrong')
            }
            window.location.href = `/company/${cid}/managing/vacations`;
        }
}
