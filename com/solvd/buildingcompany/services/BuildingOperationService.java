public class BuildingOperationService {
    private static final Logger LOGGER = LogManager.getLogger(BuildingOperationService.class);
    private static final Map<String, BuildingOperationInfo> operationInfoCache = new ConcurrentHashMap<>();
