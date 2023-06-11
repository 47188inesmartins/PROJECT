import * as React from "react";
import {useState} from "react";
import "../Style/CreatingCompany.css"
import "../Style/CreatingSchedule.css"
import 'rc-picker/assets/index.css';


export function CreatingSchedule() {
    const daysOfWeek = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];

    const [schedule, setSchedule] = useState(
        daysOfWeek.map(day => ({
            dayOfWeek: day,
            selected: false,
            startTime: '',
            endTime: ''
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
            const startTime = selectedDays[0].startTime;
            const endTime = selectedDays[0].endTime;

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

    function handleCreate(){
       console.log(schedule)
    };



    return (
        <div>
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
                                                    {day.dayOfWeek}
                                                </label>
                                                <input
                                                    type="time"
                                                    value={day.startTime}
                                                    onChange={e => handleTimeChange(index, 'startTime', e.target.value)}
                                                />
                                                <input
                                                    type="time"
                                                    value={day.endTime}
                                                    onChange={e => handleTimeChange(index, 'endTime', e.target.value)}
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
                                                onClick={()=>{}}>Cancel
                                        </button>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

        </div>
    );

}
