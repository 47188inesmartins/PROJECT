import * as React from "react";
import {useState} from "react";
import "../Style/CreatingCompany.css"
import "../Style/CreatingSchedule.css"
import 'rc-picker/assets/index.css';
import {Fetch} from "../Utils/useFetch";
import {useParams} from "react-router-dom";


export function CreatingSchedule() {
    const daysOfWeek = ['MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN'];

    const [create, setCreate] = useState<boolean>(false)

    const [schedule, setSchedule] = useState(
        daysOfWeek.map(day => ({
            weekDays: day,
            selected: false,
            beginHour: '',
            endHour: ''
        }))
    );

    const handleTimeChange = (index, field, value) => {
        setSchedule(prevSchedule => {
            const updatedSchedule = [...prevSchedule];
            updatedSchedule[index][field] = value;
            return updatedSchedule;
        });
    };

    const handleSameTimeButtonClick = () => {
        const selectedDays = schedule.filter(day => day.selected);

        if (selectedDays.length > 0) {
            const startTime = selectedDays[0].beginHour;
            const endTime = selectedDays[0].endHour;

            setSchedule(prevSchedule =>
                prevSchedule.map(day => {
                    if (day.selected) {
                        return { ...day, startTime, endTime };
                    }
                    return day;
                })
            );
        }
    };

    const handleCheckboxChange = (index, checked) => {
        setSchedule(prevSchedule => {
            const updatedSchedule = [...prevSchedule];
            updatedSchedule[index].selected = checked;
            return updatedSchedule;
        });
    };

    function FetchCreateDays(){
        const params = useParams()
        const id = params.id
        const body =  schedule
            .filter(day => day.beginHour !== '' && day.endHour !== '')
            .map(day => ({
                weekDays: day.weekDays,
                beginHour: day.beginHour,
                endHour: day.endHour
            }))
            const resp = Fetch(`company/${id}/day/all`,
                'POST',
                body).response
            if(!resp) return(<p>...loading...</p>);
            if(resp.status) {
                setCreate(false)
                window.location.href = `/`
                return(<></>);
            }
            if(resp){
                console.log(resp)
                const companyId = resp.id
                console.log("company = ", companyId)
                window.location.href = `/company/${companyId}/schedule`
                return(
                    <></>
                )
        }
    }

    function handleCreate(){
        setCreate(true)
        console.log(
            schedule
                .filter(day => day.beginHour !== '' && day.endHour !== '')
                .map(day => ({
                    week_days: day.weekDays,
                    begin_hour: day.beginHour,
                    end_hour: day.endHour
                }))
        );
    }



    return (
        <div>
            {!create ?
                <section className="vh-100 gradient-custom">
                    <div className="container py-5 h-100">
                        <div className="row d-flex justify-content-center align-items-center h-100">
                            <div className="col-12 col-md-8 col-lg-6 col-xl-5">
                                <div className="card bg-dark text-white" style={{borderRadius: '1rem'}}>
                                    <div className="card-body p-5 text-center">
                                        <div className="mb-md-5 mt-md-4 pb-5">
                                            <h2 className="fw-bold mb-2 text-uppercase">
                                                Personalize your schedule
                                            </h2>
                                            <br/><br/>
                                            <div>
                                                {schedule.map((day, index) => (
                                                    <div key={index}>
                                                        <label>
                                                            <input
                                                                type="checkbox"
                                                                checked={day.selected}
                                                                onChange={e => handleCheckboxChange(index, e.target.checked)}
                                                            />
                                                            {day.weekDays}
                                                        </label>
                                                        <input
                                                            type="time"
                                                            value={day.beginHour}
                                                            onChange={e => handleTimeChange(index, 'beginHour', e.target.value)}
                                                        />
                                                        <input
                                                            type="time"
                                                            value={day.endHour}
                                                            onChange={e => handleTimeChange(index, 'endHour', e.target.value)}
                                                        />
                                                    </div>
                                                ))}
                                                <button onClick={handleSameTimeButtonClick}>Mesmo horário</button>
                                            </div>
                                            <br/><br/>

                                            <button className="btn btn-outline-light btn-lg px-5" type="submit"
                                                    onClick={handleCreate}>
                                                Create
                                            </button>
                                            <br/>
                                            <br/>
                                            <br/>
                                            <button className="btn btn-outline-light btn-lg px-5" type="submit"
                                                    onClick={() => {
                                                    }}>Cancel
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                :
                <FetchCreateDays/>
            }
        </div>
    );
}
