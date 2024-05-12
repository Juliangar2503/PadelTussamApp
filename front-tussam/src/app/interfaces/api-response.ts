import { Player } from "./player";

export interface ApiResponse {
    data: Player;
    message: string | null;
    statusCode: {
        value: number;
        description: string;
    };
}

