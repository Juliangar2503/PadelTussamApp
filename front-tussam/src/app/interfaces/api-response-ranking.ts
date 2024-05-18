import { Player } from "./player";

export interface ApiResponseRanking {
    data: Player[];
    message: string | null;
    statusCode: {
        value: number;
        description: string;
    };
}
