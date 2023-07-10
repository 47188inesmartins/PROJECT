import React, { useState } from "react";

export function Edit (){
    const daysOfWeek = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"];

    const [schedule, setSchedule] = useState(
        daysOfWeek.map((day) => ({
            day,
            begin: "00:00",
            end: "00:00",
            lunchBegin: "00:00",
            lunchEnd: "00:00",
            isEditing: false
        }))
    );

    const handleEditClick = (index) => {
        setSchedule((prevSchedule) =>
            prevSchedule.map((item, i) =>
                i === index ? { ...item, isEditing: true } : { ...item, isEditing: false }
            )
        );
    };

    const handleDoneClick = (index) => {
        setSchedule((prevSchedule) =>
            prevSchedule.map((item, i) =>
                i === index ? { ...item, isEditing: false } : item
            )
        );
    };

    const handleTimeChange = (index, field, value) => {
        setSchedule((prevSchedule) =>
            prevSchedule.map((item, i) =>
                i === index ? { ...item, [field]: value } : item
            )
        );
    };

    const renderTable = () => {
        return schedule.map((item, index) => (
            <tr key={index}>
                <td>{item.day}</td>
                <td>
                    {item.isEditing ? (
                        <input
                            type="time"
                            value={item.begin}
                            onChange={(e) => handleTimeChange(index, "begin", e.target.value)}
                        />
                    ) : (
                        item.begin
                    )}
                </td>
                <td>
                    {item.isEditing ? (
                        <input
                            type="time"
                            value={item.end}
                            onChange={(e) => handleTimeChange(index, "end", e.target.value)}
                        />
                    ) : (
                        item.end
                    )}
                </td>
                <td>
                    {item.isEditing ? (
                        <input
                            type="time"
                            value={item.lunchBegin}
                            onChange={(e) => handleTimeChange(index, "lunchBegin", e.target.value)}
                        />
                    ) : (
                        item.lunchBegin
                    )}
                </td>
                <td>
                    {item.isEditing ? (
                        <input
                            type="time"
                            value={item.lunchEnd}
                            onChange={(e) => handleTimeChange(index, "lunchEnd", e.target.value)}
                        />
                    ) : (
                        item.lunchEnd
                    )}
                </td>
                <td>
                    {item.isEditing ? (
                        <button onClick={() => handleDoneClick(index)}>Done</button>
                    ) : (
                        <button onClick={() => handleEditClick(index)}>Edit</button>
                    )}
                </td>
            </tr>
        ));
    };

    return (
        <div className="card" style={{ backgroundColor: "#e8f1f5", padding: "20px", maxWidth: "550px", margin: "0 auto", position: "absolute", top: "50%", left: "50%", transform: "translate(-50%, -50%)" }}>
            <table>
                <thead>
                <tr>
                    <th>Week Day</th>
                    <th>Hour Begin</th>
                    <th>Hour End</th>
                    <th>Lunch Begin</th>
                    <th>Lunch End</th>
                    <th>Edit</th>
                </tr>
                </thead>
                <tbody>{renderTable()}</tbody>
            </table>
        </div>
    );

};
