import { League } from "./league";

export interface ApiResponseLeagues {
    data: League[];
    message: string | null;
    statusCode: {
        value: number;
        description: string;
    };
}
