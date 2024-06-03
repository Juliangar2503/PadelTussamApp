import { StatsPlayer } from "./stats-player";

export interface StatsPlayerApiResponse {

    data: StatsPlayer;
    message: string | null;
    statusCode: {
        value: number;
        description: string;
    };

}
